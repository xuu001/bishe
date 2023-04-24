package xyz.hhang.boot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.hhang.boot.dao.DataDao;
import xyz.hhang.boot.exception.ServiceException;
import xyz.hhang.boot.service.DataService;
import xyz.hhang.boot.utils.ConstantUtil;
import xyz.hhang.boot.utils.PasswordUtil;

import java.util.List;
import java.util.Map;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataDao dataDao;
    @Autowired
    private MailService mailService;

    @Override
    public Map<String, Object> findReport(Map<String, Object> param) throws ServiceException {
        try {
            return dataDao.findReport(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return param;
    }

    @Override
    public Map<String, Object> register(Map<String, Object> data) throws ServiceException {
        String company = (String) data.get("company");
        Map<String, Object> byCompany = null;
        String password = (String) data.get("pass");
        String email = (String) data.get("mail");

        try {
            byCompany = dataDao.findCompany(company);
            if (byCompany == null) {
                String passwordEncoded = PasswordUtil.encode(password);
                data.put("pass", passwordEncoded);
                dataDao.saveCompany(data);
                dataDao.saveAccount(data);
                dataDao.saveRoleAccount(data);
                mailService.sendRegisterMail(email);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("数据库操作异常",e);
        }
        if(byCompany != null){
            // 说明用户已存在
            throw new ServiceException("该公司已被注册");
            }
        return data;
    }
    //企业信息更新
    @Override
    public int update(Map<String, Object> param) throws ServiceException {
        int res = -1;
        try {
            res = dataDao.update(param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("数据更新异常", e);
        }
        if(res == 0){
            // id version 不匹配
            throw new ServiceException("修改失败，该数据可能已被其他用户修改，请返回后重试");
        }
        return res;
    }

    @Override
    public Map<String,Object> findById(Map<String,Object> param) throws ServiceException {
        try {
           Map<String,Object> company = dataDao.findById(param);
            return  company;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return param;
    }
    //企业信息审核
    @Override
    public int upStatus(Map<String, Object> param) throws ServiceException {
        int res = -2;
        try {
            res = dataDao.upStatus(param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("数据更新异常", e);
        }
        if (res == 0) {
            // id version 不匹配
            throw new ServiceException("修改失败，该数据可能已被其他用户修改，请返回后重试");
        }
        return res;
    }


}

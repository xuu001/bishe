package xyz.hhang.boot.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.hhang.boot.dao.UserDao;
import xyz.hhang.boot.exception.ServiceException;
import xyz.hhang.boot.service.UserService;
import xyz.hhang.boot.utils.ConstantUtil;
import xyz.hhang.boot.utils.PasswordUtil;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailService mailService;


    @Override
    public Map<String, Object> login(Map<String, Object> user) throws ServiceException {
        String account = (String) user.get("account");
        String password = (String) user.get("password");
        Map<String, Object> ByAcc = null;
        try {
            ByAcc = userDao.findByAcc(account);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("数据库操作异常", e);
        }
        if (ByAcc == null || ByAcc.isEmpty()) {
            throw new ServiceException("用户不存在");
        }
        //  ======== 密码加密部分
        boolean res = PasswordUtil.chack(password, (String) ByAcc.get("password"));
        if (res) {
            // 登陆成功
            return ByAcc;
        } else {
            throw new ServiceException("密码错误");
        }
    }

    @Override
    public List<Map<String, Object>> findMenu(Map<String, Object> param) throws ServiceException {
        try {
            return userDao.findMenu(param);
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException("服务查询异常",e);
        }

    }


    @Override
    public Map<String, Object> saveUser(Map<String, Object> user) throws ServiceException {
        // 判断用户是否存在
        String account = (String) user.get("account");
        Map<String, Object> byAccount = null;
        String password = (String) user.get("pass");
        try {
            byAccount = userDao.findByAcc(account);
            if(byAccount == null){
                String passwordEncoded = PasswordUtil.encode(password);
                user.put("password",passwordEncoded);
                userDao.saveUser(user);
                userDao.saveDep_acc(user);
                userDao.saveRole(user);
                userDao.upDepartment(user);
//                userDao.saveDep_acc(user);
//                userDao.saveRole(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("数据库操作异常", e);
        }
        if(byAccount != null){
            // 说明用户已存在
            throw new ServiceException("用户已存在");
        }
        return user;
    }

    /**
     *  添加员工的同时插入权限管理以及部门关联 以及部门人数加一
     * @param depart 传来的 id 等
     * @throws ServiceException
     */
    @Override
    public Map<String,Object> saveDep_acc_role(Map<String, Object> depart) throws ServiceException {
        try {
            userDao.saveDep_acc(depart);
            userDao.saveRole(depart);
            userDao.upDepartment(depart);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("error_s权限/部门关联失败");
        }
        return depart;
    }

    @Override
    public int changePass(Map<String, Object> param) throws ServiceException {
        if(!param.get("password").equals(param.get("repassword"))) {
            throw new ServiceException("两次密码不一致");
        }
        try {
            String password = (String) param.get("password");
            password = PasswordUtil.encode(password);
            param.put("password", password);
            return userDao.changePass(param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("密码修改失败");
        }

    }

    @Override
    public Map<String, Object> exist(String mail) throws ServiceException {
        try {
            return userDao.findByAccount(mail);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public int update(Map<String, Object> param) throws ServiceException {
        try {
            return userDao.update(param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("用户修改失败", e);
        }
    }

    @Override
    public PageInfo<Map<String, Object>> findUser(Map<String, Object> param) throws ServiceException {
        try {
            Object pageObj = param.get("page");
            Object sizeObj = param.get("size");
            Integer page = 1;
            Integer size = ConstantUtil.SIZE;
            if (pageObj != null) {
                page = Integer.valueOf((String) pageObj);
                if (page < 1) {
                    page = 1;
                }
            }
            if (sizeObj != null) {
                size = Integer.valueOf((String) sizeObj);
            }
            PageHelper.startPage(page,size);
            List<Map<String, Object>> UserList = userDao.findUser(param);
            PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(UserList);
            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("服务查询异常", e);
        }
    }

    @Override
    public int delete(List<Long> ids) throws ServiceException {
        try {
            return userDao.delete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("删除失败", e);
        }
    }

    @Override
    public int upUser(Map<String, Object> param) throws ServiceException {
        int res = -2;
        try {
            res = userDao.upUser(param);
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

package xyz.hhang.boot.service.impl;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.hhang.boot.dao.CompanyDao;
import xyz.hhang.boot.exception.ServiceException;
import xyz.hhang.boot.service.CompanyService;


import java.util.List;
import java.util.Map;


@Service
public class CompanyImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;


    @Override
    public List<Map<String, Object>> findDepartment(Map<String, Object> param) throws ServiceException {
        try {
            return companyDao.findDepartment(param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("error_c 数据查询失败",e);
        }
    }

    @Override
    public int save(Map<String, Object> param) throws ServiceException {
        try {
            return companyDao.save(param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("增加异常", e);
        }
    }

    @Override
    public int update(Map<String, Object> param) throws ServiceException {
        try {
            return companyDao.update(param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("修改异常", e);
        }
    }

    @Override
    public int delete(List<Long> ids) throws ServiceException {
        try {
            return companyDao.delete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("删除失败", e);
        }
    }
}


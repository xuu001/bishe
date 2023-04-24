package xyz.hhang.boot.service.impl;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.hhang.boot.dao.OutDao;
import xyz.hhang.boot.exception.ServiceException;
import xyz.hhang.boot.service.OutService;

import java.util.List;
import java.util.Map;

@Service
public class OutServiceImpl implements OutService {
    @Autowired
    private OutDao busApplyDao;


    @Override
    public int save(Map<String, Object> param) throws ServiceException {
        try {
            return busApplyDao.save(param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("申请失败error_s", e);
        }
    }


    @Override
    public int audit(Map<String, Object> param) throws ServiceException {
        try {
            return busApplyDao.audit(param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("提交失败error_s", e);
        }
    }


    @Override
    public PageInfo<Map<String, Object>> getAllList(Map<String, Object> param) throws ServiceException {
        DaKaServiceImpl.pageMap(param); //打卡serviceImpl封装了分页的java公共部分
        List<Map<String, Object>> data;
        try {
            data = busApplyDao.getAllList(param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("数据查询失败error_s", e);
        }
        PageInfo<Map<String, Object>> pageData = new PageInfo<>(data);

        return pageData;

    }

    @Override
    public PageInfo<Map<String, Object>> findByro(Map<String, Object> param) throws ServiceException {
        DaKaServiceImpl.pageMap(param);
        List<Map<String, Object>> data;
        try {
            data = busApplyDao.findByro(param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("数据查询失败error_s", e);
        }
        PageInfo<Map<String, Object>> pageData = new PageInfo<>(data);
        return pageData;

    }


}






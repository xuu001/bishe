package xyz.hhang.boot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.hhang.boot.dao.TripDao;
import xyz.hhang.boot.exception.ServiceException;
import xyz.hhang.boot.service.TripService;
import xyz.hhang.boot.utils.ConstantUtil;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class TripServiceImpl implements TripService {
    @Autowired
    private TripDao tripDao;

    @Override
    public int save(Map<String, Object> params) throws ServiceException {
        try {
            return tripDao.save(params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("error_s数据提交失败", e);
        }
    }

    @Override
    public PageInfo<Map<String, Object>> getList(Map<String, Object> params) throws ServiceException {
        DaKaServiceImpl.pageMap(params);//复用打卡service里的分页部分逻辑
        List<Map<String, Object>> data;
        try {
            data = tripDao.getList(params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("error_s查询失败", e);
        }
        PageInfo<Map<String, Object>> list = new PageInfo<>(data);
        return list;
    }

    //先定义需要导出的数据集合
    List<Map<String, Object>> exportData;
    @Override
    public PageInfo<Map<String, Object>> getAllList(Map<String, Object> params) throws ServiceException {
       //DaKaServiceImpl.pageMap(params);
        List<Map<String, Object>> data;
        try {
            exportData = tripDao.getAllList(params);
            DaKaServiceImpl.pageMap(params);
            data = tripDao.getAllList(params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("error_s查询失败", e);
        }
        PageInfo<Map<String, Object>> list = new PageInfo<>(data);
        return list;
    }

    //导出查询的不分页，获取符合的全部数据
    @Override
    public List<Map<String, Object>> exportList(Map<String, Object> params) throws ServiceException {
//        int page = 1;
//        int size = 999;
//        PageHelper.startPage(page,size);
//        List<Map<String,Object>> data;
        try {
            //List<Map<String,Object>> data = tripDao.getAllList(params);
            return exportData;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("error_s查询失败", e);
        }
    }
}

package xyz.hhang.boot.service.impl;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.hhang.boot.dao.ReWorkDao;
import xyz.hhang.boot.exception.ServiceException;
import xyz.hhang.boot.service.ReWorkService;

import java.util.List;
import java.util.Map;

@Service
public class ReWorkServiceImpl implements ReWorkService {

    @Autowired
    private ReWorkDao cusLevelDao;

    @Override
    public PageInfo<Map<String, Object>> findByCus(Map<String, Object> param) throws ServiceException {
        DaKaServiceImpl.pageMap(param);
        List<Map<String, Object>> data;
        try {
            data = cusLevelDao.findByCus(param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("数据查询失败",e);
        }
        PageInfo<Map<String,Object>> pageData=new PageInfo<>(data);
        return pageData;

    }

    @Override
    public int save(Map<String, Object> param) throws ServiceException {
        try {
            return cusLevelDao.save(param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("增加异常", e);
        }
    }

    @Override
    public int update(Map<String, Object> param) throws ServiceException {
        try {
            return cusLevelDao.update(param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("修改异常", e);
        }
    }

    @Override
    public int delete(List<Long> ids) throws ServiceException {
        try {
            return cusLevelDao.delete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("删除失败", e);
        }
    }
}

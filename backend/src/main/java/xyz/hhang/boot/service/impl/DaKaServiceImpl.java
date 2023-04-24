package xyz.hhang.boot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.hhang.boot.dao.DaKaDao;
import xyz.hhang.boot.exception.ServiceException;
import xyz.hhang.boot.service.DaKaService;
import xyz.hhang.boot.utils.ConstantUtil;

import java.util.List;
import java.util.Map;

@Service
public class DaKaServiceImpl implements DaKaService {
    @Autowired
    private DaKaDao daKaDao;

    @Override
    public int save(Map<String, Object> param) throws ServiceException {
        try {
            return daKaDao.save(param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("error_s数据提交失败", e);
        }
    }

    @Override
    public PageInfo<Map<String, Object>> getList(Map<String, Object> param) throws ServiceException {
        pageMap(param);
        List<Map<String, Object>> data;
        try {
             data = daKaDao.getList(param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("error_s数据查询失败",e);
        }
        PageInfo<Map<String,Object>> pageData = new PageInfo<>(data);
        return pageData;
    }

    @Override
    public PageInfo<Map<String, Object>> getAllList(Map<String, Object> param) throws ServiceException {
        pageMap(param);
        List<Map<String, Object>> data;
        try {
            data = daKaDao.getAllList(param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("error_s数据查询失败",e);
        }
        PageInfo<Map<String,Object>> pageData = new PageInfo<>(data);
        return pageData;
    }


    //公用分页前缀
    public static void pageMap(Map<String, Object> param) {
        Object pageObj = param.get("page");
        Object sizeObj = param.get("size");
        Integer page = 1;
        Integer size = ConstantUtil.SIZE;
        if (pageObj != null){
            page = Integer.valueOf((String) pageObj);
            if (page < 1){
                page = 1;
            }
        }
        if (sizeObj != null){
            size = Integer.valueOf((String) sizeObj);
        }
        PageHelper.startPage(page,size);
    }
}

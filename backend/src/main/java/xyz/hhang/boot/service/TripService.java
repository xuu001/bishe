package xyz.hhang.boot.service;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import xyz.hhang.boot.exception.ServiceException;

import java.util.List;
import java.util.Map;
@Service
public interface TripService {
    int save(Map<String,Object> params) throws ServiceException;

    PageInfo<Map<String,Object>> getList(Map<String,Object> params) throws ServiceException;

    PageInfo<Map<String,Object>> getAllList(Map<String,Object> params) throws ServiceException;

    List<Map<String,Object>> exportList(Map<String,Object> params) throws ServiceException;
}

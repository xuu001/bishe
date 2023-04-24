package xyz.hhang.boot.service;

import com.github.pagehelper.PageInfo;
import xyz.hhang.boot.exception.ServiceException;

import java.util.Map;

public interface DataService {
    Map<String, Object> findReport(Map<String, Object> param) throws ServiceException;

    Map<String, Object> register(Map<String, Object> user) throws ServiceException;

    int update(Map<String, Object> param) throws ServiceException;

    Map<String, Object> findById(Map<String, Object> param) throws ServiceException;

    int upStatus(Map<String, Object> param) throws ServiceException;
}

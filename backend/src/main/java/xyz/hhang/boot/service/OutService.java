package xyz.hhang.boot.service;


import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import xyz.hhang.boot.exception.ServiceException;

import java.util.Map;

public interface OutService {

    int save(Map<String, Object> param) throws ServiceException;

    int audit(Map<String, Object> param) throws ServiceException;

    PageInfo<Map<String, Object>> getAllList(Map<String, Object> param) throws ServiceException;

    PageInfo<Map<String, Object>> findByro(Map<String, Object> param) throws ServiceException;


}

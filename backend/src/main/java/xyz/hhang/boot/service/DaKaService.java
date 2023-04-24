package xyz.hhang.boot.service;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import xyz.hhang.boot.exception.ServiceException;

import java.util.Map;

@Service
public interface DaKaService {
    int save(Map<String,Object> param) throws ServiceException;

    PageInfo<Map<String,Object>> getList(Map<String,Object> param) throws ServiceException;

    PageInfo<Map<String,Object>> getAllList(Map<String,Object> param) throws ServiceException;
}

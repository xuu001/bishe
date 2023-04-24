package xyz.hhang.boot.service;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import xyz.hhang.boot.exception.ServiceException;

import java.util.List;
import java.util.Map;

@Service
public interface CompanyService {

    List<Map<String, Object>> findDepartment(Map<String, Object> param) throws ServiceException;

    int save(Map<String, Object> param) throws ServiceException;

    int update(Map<String, Object> param) throws ServiceException;

    int delete(List<Long> ids) throws  ServiceException;
}

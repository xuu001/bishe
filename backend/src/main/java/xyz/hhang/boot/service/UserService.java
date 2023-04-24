package xyz.hhang.boot.service;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import xyz.hhang.boot.exception.ServiceException;


import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    Map<String, Object> login(Map<String,Object> user) throws ServiceException;

    List<Map<String,Object>> findMenu(Map<String,Object> param) throws ServiceException;

    int changePass(Map<String, Object> param) throws ServiceException;
    Map<String,Object> exist(String mail) throws ServiceException;
    int update(Map<String, Object> param) throws ServiceException;
    PageInfo<Map<String,Object>> findUser(Map<String,Object> param) throws ServiceException;

    int delete(List<Long> ids) throws  ServiceException;

    Map<String, Object> saveUser(Map<String, Object> user) throws ServiceException;

    Map<String,Object> saveDep_acc_role(Map<String,Object> depart) throws ServiceException;

    int upUser(Map<String, Object> param)throws ServiceException;
}

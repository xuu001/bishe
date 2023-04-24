package xyz.hhang.boot.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
@Mapper
public interface DataDao {
    Map<String,Object> findReport (Map<String,Object> param)throws Exception;

    Map<String,Object> findCompany (String company)throws Exception;

    Map<String,Object> findById(Map<String, Object> company) throws Exception;

    void saveCompany(Map<String,Object> param)throws Exception;

    int saveAccount(Map<String,Object> param)throws Exception;

    int saveRoleAccount(Map<String,Object> param)throws Exception;

    int update(Map<String,Object> param)throws Exception;

    int upStatus(Map<String,Object> param) throws  Exception;

}

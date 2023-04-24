package xyz.hhang.boot.dao;

import org.apache.ibatis.annotations.Mapper;


import java.util.List;

import java.util.Map;

@Mapper
public interface CompanyDao {
        List<Map<String,Object>> findDepartment(Map<String,Object> condition) throws Exception;

        int save(Map<String, Object> param) throws  Exception;

        int update(Map<String, Object> param) throws Exception;

        int delete(List<Long> ids) throws Exception;

}

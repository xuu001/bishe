package xyz.hhang.boot.dao;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReWorkDao {


    List<Map<String,Object>> findByCus(Map<String,Object> condition) throws Exception;

    int save(Map<String, Object> param) throws  Exception;

    int update(Map<String, Object> param) throws Exception;

    int delete(List<Long> ids) throws Exception;
}

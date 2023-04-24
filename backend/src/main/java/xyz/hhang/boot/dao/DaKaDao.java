package xyz.hhang.boot.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DaKaDao {
    int save(Map<String, Object> param) throws Exception;

    List<Map<String, Object>> getList(Map<String, Object> condition) throws Exception;

    List<Map<String,Object>> getAllList(Map<String,Object> condition) throws Exception;
}

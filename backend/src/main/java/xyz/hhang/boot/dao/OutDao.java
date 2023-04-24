package xyz.hhang.boot.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OutDao {

    int save(Map<String, Object> param) throws Exception;

    int audit(Map<String, Object> param) throws Exception;

    List<Map<String,Object>> getAllList(Map<String,Object> condition) throws Exception;

    List<Map<String,Object>> findByro(Map<String,Object> condition) throws Exception;


}

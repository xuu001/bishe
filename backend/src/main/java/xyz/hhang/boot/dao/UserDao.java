package xyz.hhang.boot.dao;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface  UserDao {

    List<Map<String, Object>> findUser(Map<String,Object> param)throws Exception;

    Map<String, Object> findByAcc(String account);

    Map<String, Object> findByAccount(String mail)throws Exception;

   List<Map<String, Object>> findMenu(Map<String,Object> param)throws Exception;

    int save( Map<String,Object> param)throws Exception;

    int changePass(Map<String,Object> param) throws Exception;

    int update(Map<String, Object> param) throws Exception;

    int upUser(Map<String,Object> param) throws  Exception;

    int saveUser( Map<String,Object> param)throws Exception;

    int saveDep_acc(Map<String,Object> param )throws Exception;
    int saveRole( Map<String,Object> param)throws Exception;
    int upDepartment(Map<String,Object> param)throws Exception;

    int delete(List<Long> ids) throws Exception;
}

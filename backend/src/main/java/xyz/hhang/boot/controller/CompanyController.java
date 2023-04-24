package xyz.hhang.boot.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.hhang.boot.exception.ServiceException;
import xyz.hhang.boot.service.CompanyService;
import xyz.hhang.boot.utils.ResponseDataBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
// 用来标识当前controller的访问路径的
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping(value = "/findDepart")
    public Map<String, Object> search (@RequestParam Map<String, Object> param) {
        try {
            List<Map<String, Object>> department = companyService.findDepartment(param);
            return new ResponseDataBuilder().success().data(department).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("查询失败").build();
        }
    }


    @PostMapping("/add")
    public Map<String,Object> add(@RequestBody Map<String,Object> param){
        try {
            int res=companyService.save(param);
            return new ResponseDataBuilder().success().message("成功").data(res).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().build();
        }
    }

    @PutMapping("/edit")
    public Map<String,Object> edit(@RequestBody Map<String, Object> param){
        try {
            int res= companyService.update(param);
            return new ResponseDataBuilder().success().message("修改成功").data(res).build();
            // 返回查询页面
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("修改失败").build();
        }
    }

    @DeleteMapping("/delete")
    public Map<String, Object> delete(@RequestParam(value = "ids") Long[] ids){
        try {
            int res = companyService.delete(Arrays.asList(ids));
            return new ResponseDataBuilder().success().data(res).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message(e.getMessage()).build();
        }
    }



}

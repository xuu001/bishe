package xyz.hhang.boot.controller;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.hhang.boot.exception.ServiceException;
import xyz.hhang.boot.service.ExpressCompanyService;
import xyz.hhang.boot.utils.ResponseDataBuilder;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/express")
public class ExpressCompanyController {

  @Autowired
  private   ExpressCompanyService  expressCompanyService;

    @GetMapping(value = "/search")
    public Map<String, Object> search (@RequestParam Map<String, Object> param) {
        try {
            PageInfo<Map<String, Object>> companyTypeList = expressCompanyService.findByexpress(param);
            return new ResponseDataBuilder().success().data(companyTypeList).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("查询失败").build();
        }
    }


    @PostMapping("/add")
    public Map<String,Object> add(@RequestBody Map<String,Object> param){
        try {
            int res=expressCompanyService.save(param);
            return new ResponseDataBuilder().success().message("成功").data(res).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().build();
        }
    }

    @PutMapping("/edit")
    public Map<String,Object> edit(@RequestBody Map<String, Object> param){
        try {
            int res= expressCompanyService.update(param);
            return new ResponseDataBuilder().success().message("修改成功").data(res).build();
            // 返回查询页面
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("修改失败").build();
        }
    }

    @DeleteMapping
    public Map<String, Object> delete(@RequestParam(value = "ids") Long[] ids){
        try {
            int res =expressCompanyService.delete(Arrays.asList(ids));
            return new ResponseDataBuilder().success().data(res).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message(e.getMessage()).build();
        }
    }

}

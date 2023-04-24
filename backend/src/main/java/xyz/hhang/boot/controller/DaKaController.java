package xyz.hhang.boot.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.hhang.boot.exception.ServiceException;
import xyz.hhang.boot.service.DaKaService;
import xyz.hhang.boot.utils.ResponseDataBuilder;

import java.util.Map;

@RestController
@RequestMapping("/daKa")
public class DaKaController {

    @Autowired
    private DaKaService daKaService;

    @PostMapping("/save")
    public Map<String,Object> save(@RequestBody Map<String,Object> param){
        try {
           int res = daKaService.save(param);
            return new ResponseDataBuilder().message("打卡成功").data(res).build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("error_c打卡失败").build();
        }
    }

    @GetMapping("/getList")
    public Map<String,Object> getList(@RequestParam Map<String,Object> param){
        try {
            PageInfo<Map<String,Object>> data= daKaService.getList(param);
            return new ResponseDataBuilder().success().data(data).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("error_c"+e.getMessage()).build();
        }
    }

    @GetMapping("/getAllList")
    public Map<String,Object> getAllList(@RequestParam Map<String,Object> param){
        try {
            PageInfo<Map<String,Object>> page= daKaService.getAllList(param);
            return new ResponseDataBuilder().success().data(page).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("error_c"+e.getMessage()).build();
        }
    }

}

package xyz.hhang.boot.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.hhang.boot.exception.ServiceException;
import xyz.hhang.boot.service.OutService;
import xyz.hhang.boot.utils.ResponseDataBuilder;

import java.util.Map;

@RestController
@RequestMapping(value = "/out")
public class OutController {

    @Autowired
    private OutService outService;

    @PostMapping("/apply")
    public Map<String, Object> add(@RequestBody Map<String, Object> param) {

        try {
            int res = outService.save(param);
            return new ResponseDataBuilder().success().message("ok_c申请成功").data(res).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("error_c"+e.getMessage()).build();
        }
    }


    @PostMapping("/audit")
    public Map<String, Object> audit(@RequestBody Map<String, Object> param) {
        try {
            int res = outService.audit(param);
            return new ResponseDataBuilder().success().data(res).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("error_c"+e.getMessage()).build();
        }
    }


    @GetMapping(value = "/getAllList")
    public Map<String, Object> getAllList(@RequestParam Map<String, Object> param) {
        try {
            PageInfo<Map<String, Object>> page = outService.getAllList(param);
            return new ResponseDataBuilder().success().data(page).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("error_c"+e.getMessage()).build();
        }


    }

    @GetMapping(value = "/findByro")
    public Map<String, Object> findByro (@RequestParam Map < String, Object > param){
        try {
            PageInfo<Map<String, Object>> findByro = outService.findByro(param);
            return new ResponseDataBuilder().success().data(findByro).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("error_c"+e.getMessage()).build();
        }

    }

}







package xyz.hhang.boot.controller;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.hhang.boot.exception.ServiceException;
import xyz.hhang.boot.service.ReWorkService;
import xyz.hhang.boot.utils.ResponseDataBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("reWork")
public class ReWorkController {

    @Value("${file.upload.path}")
    private String path;
    @Autowired
    private ReWorkService reWorkService;


    @GetMapping(value = "/getList")
    public Map<String, Object> search (@RequestParam Map<String, Object> param) {
        try {
            PageInfo<Map<String, Object>> companyTypeList = reWorkService.findByCus(param);
            return new ResponseDataBuilder().success().message("查询成功").data(companyTypeList).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("查询失败").build();
        }
    }


    @PostMapping("/apply")
    public Map<String,Object> add(@RequestParam("file") MultipartFile file,@RequestParam Map<String,Object> param) throws IOException {
        //上传的图片规定一个唯一一个随机名称放入库中
        String uuid = UUID.randomUUID().toString();
        String filename = file.getOriginalFilename();
        String externalName = filename.substring(filename.lastIndexOf("."));
        String storeName = uuid + externalName;

        file.transferTo(new File(path +storeName));
        param.put("file",storeName);

        try {
            int res= reWorkService.save(param);
            return new ResponseDataBuilder().success().message("申请成功").data(res).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message(e.getMessage()).build();
        }
    }

    @PostMapping("/edit")
    public Map<String,Object> edit(@RequestBody Map<String, Object> param){
        try {
            int res= reWorkService.update(param);
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
            int res = reWorkService.delete(Arrays.asList(ids));
            return new ResponseDataBuilder().success().data(res).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message(e.getMessage()).build();
        }
    }



}

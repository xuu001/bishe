package xyz.hhang.boot.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.hhang.boot.exception.ServiceException;
import xyz.hhang.boot.service.DataService;
import xyz.hhang.boot.utils.ResponseDataBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/data")
public class DataController {
    @Autowired
    private DataService companyService;

    @Value("${file.upload.path}")
    private String path;

    @GetMapping(value = "/findReport")
    public Map<String,Object> search (@RequestParam Map<String,Object> param){
        try {
            Map<String, Object> num = companyService.findReport(param);
            return new ResponseDataBuilder().success().data(num).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("error_c;查询失败").build();
        }
    }

    @GetMapping(value = "/findById")
    public Map<String,Object> findById(@RequestParam Map<String,Object> param){
        try {
            Map<String,Object> company = companyService.findById(param);
            return new ResponseDataBuilder().success().data(company).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("查询失败").build();
        }
    }

    @PostMapping("/register")
    public Map<String,Object> register(@RequestBody Map<String,Object> company){
        ResponseDataBuilder responseDataBuilder = new ResponseDataBuilder();
        try {
            Map<String,Object> result = companyService.register(company);
            responseDataBuilder.success().data(company);
        } catch (ServiceException e) {
            e.printStackTrace();
            responseDataBuilder.error().message(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            responseDataBuilder.error().message("注册失败");
        }
        return responseDataBuilder.build();
    }

    @PostMapping(value = "/update")
    public  Map<String,Object> update(@RequestParam Map<String,Object> param, @RequestParam("background1") MultipartFile file1,@RequestParam("background2") MultipartFile file2) throws IOException {
        String uuid1 = UUID.randomUUID().toString();
        String uuid2 = UUID.randomUUID().toString();
        String filename1 = file1.getOriginalFilename();
        String filename2 = file2.getOriginalFilename();
        String externalName1 = filename1.substring(filename1.lastIndexOf("."));
        String externalName2 = filename2.substring(filename2.lastIndexOf("."));
        String storeName1 = uuid1 + externalName1;
        String storeName2 = uuid2 + externalName2;
        file1.transferTo(new File(path + storeName1));
        file2.transferTo(new File(path + storeName2));
        param.put("cerificateAttachment1", storeName1);
        param.put("cerificateAttachment2", storeName2);
        try {
           int res = companyService.update(param);
            return new ResponseDataBuilder().success().message("修改成功").data(res).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("修改失败").build();
        }
    }

    @PostMapping(value = "/upStatus")
    public  Map<String,Object> upStatus(@RequestBody Map<String,Object> param){
        int res = 0;
        try {
            res = companyService.upStatus(param);
            return new ResponseDataBuilder().success().message("修改成功").data(res).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("修改失败").build();
        }
    }
}

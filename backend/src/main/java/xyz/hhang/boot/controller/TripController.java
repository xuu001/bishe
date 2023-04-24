package xyz.hhang.boot.controller;

import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.hhang.boot.exception.ServiceException;
import xyz.hhang.boot.service.TripService;
import xyz.hhang.boot.utils.ExcelUtil;
import xyz.hhang.boot.utils.ResponseDataBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trip")
public class TripController  {
    @Autowired
    private TripService tripService;

    @PostMapping("/save")
    public Map<String,Object> save(@RequestBody Map<String,Object> params){
        try {
            int res = tripService.save(params);
            return new ResponseDataBuilder().success().message("上报成功").data(res).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("error_c上报失败").build();
        }
    }
    @GetMapping("/getList")
    public Map<String,Object> getList(@RequestParam Map<String,Object> params){
        try {
            PageInfo<Map<String,Object>> data = tripService.getList(params);
            return new ResponseDataBuilder().success().data(data).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("error_c"+e.getMessage()).build();
        }
    }
    @GetMapping("/getAllList")
    public Map<String,Object> getAllList(@RequestParam Map<String,Object> params){
        try {
            PageInfo<Map<String,Object>> data = tripService.getAllList(params);
            return new ResponseDataBuilder().success().data(data).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("error_c"+e.getMessage()).build();
        }
    }

    //直接导出 需要自己加入导出表头 关键字
    @GetMapping("/export")
    public void getExportList(@RequestParam Map<String,Object> params, HttpServletResponse response){
        try {
            List<Map<String,Object>> data = tripService.exportList(params);
            String[] titles = { "报备日期", "姓名", "部门", "工号","出发日期","出行交通工具","抵达日期","回来日期","回来的交通工具","出行目的地","是否风险区","电话号码","备注"};
            String[] dataKeys = {"createTime","name","department","account","departureTime","transport","arrivalTime","returnTime","returnTransport","destination","isRisk","phone","remarks"};
            Workbook workbook = ExcelUtil.export(data,titles,dataKeys);
            String fileName = "行程导出.xlsx";
            //对文件名编码
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1);
            //设置响应编码
            response.setCharacterEncoding("utf-8");
            //设置响应数据以流的形式返回
            response.setContentType("application/octet-stream");
            //设置返回的文件名称
            String header = "attachment;filename=\""+fileName+"\"";
            response.addHeader("Content-Disposition",header);
            //获取response的输出流
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
//            return new ResponseDataBuilder().success().build();
        } catch (ServiceException | IOException e) {
            e.printStackTrace();
//            return new ResponseDataBuilder().error().message("error_c"+e.getMessage()).build();
        }
    }

}

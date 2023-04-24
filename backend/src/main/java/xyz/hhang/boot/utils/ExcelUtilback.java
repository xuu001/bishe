package xyz.hhang.boot.utils;//package xyz.hhang.utils;
//
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.core.io.ClassPathResource;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class ExcelUtilback {
//
//    //直接导出
//    public static Workbook export(List<Map<String, Object>> list, String[] titles, String[] dataKeys) throws IOException {
//        //创建一共workbook
//       Workbook workbook = new XSSFWorkbook();
//        //创建sheet
//        Sheet sheet = workbook.createSheet("数据");
//        //创建表头
//        Row titleRow = sheet.createRow(0);
//        for (int i=0;i< titles.length;i++){
//            Cell cell = titleRow.createCell(i);
//            cell.setCellValue(titles[i]);
//            //公共  添加边框
//            addCellBorder(workbook ,cell);
//        }
//
//        for (int i=0;i<list.size();i++){
//            Map<String,Object> map = list.get(i);
//            Row row = sheet.createRow(i+1);
//            for (int j=0;j< dataKeys.length;j++){
//                Cell cell= row.createCell(j);
//                cell.setCellValue(String.valueOf(map.get(dataKeys[j])));
//
//                addCellBorder(workbook,cell);
//            }
//        }
//
//        sheet.setColumnWidth(0,12*256);
//        sheet.setColumnWidth(2,45*256);
//
//        return workbook;
//    }
//    //按照模板导出
//    public static Workbook exportWithTemplate(String templateName,List<Map<String,Object>>data,String[] dataKeys)throws IOException {
//        ClassPathResource resource = new ClassPathResource(templateName);
//        Workbook workbook = new XSSFWorkbook(resource.getInputStream());
//        Sheet sheet = workbook.getSheetAt(0);
//        Row templateRow = sheet.getRow(2);
//        for (int i=0;i<data.size();i++) {
//            if (i==0){
//                for (int j=0;j<dataKeys.length;j++){
//                    Map<String,Object> map=data.get(i);
//                    Cell cell=templateRow.getCell(j + 1);
//                    cell.setCellValue(String.valueOf(map.get(dataKeys[j])));
//                }
//            }else {
//                Row row=sheet.createRow(i + 2);
//                for (int j=0;j<dataKeys.length;j++){
//                    Map<String,Object> map=data.get(i);
//                    Cell cell=row.createCell(j+1);
//                    cell.setCellStyle(templateRow.getCell(j+1).getCellStyle());
//                    cell.setCellValue(String.valueOf(map.get(dataKeys[j])));
//                }
//            }
//        }
//        return  workbook;
//
//    }
//
//
//    public static List<Map<String,Object>> importData(InputStream inputStream) throws IOException{
//        List<Map<String,Object>> datas = new ArrayList<>();
//        Workbook workbook = new XSSFWorkbook(inputStream);
//
//        Sheet sheet = workbook.getSheetAt(0);
//        Row keysRow = sheet.getRow(0);
//        int rows = sheet.getPhysicalNumberOfRows();
//        for (int i= 2;i<rows;i++){
//            Row row = sheet.getRow(i);
//            Map<String,Object> map=new HashMap<>();
//            int cols = row.getPhysicalNumberOfCells();
//            for (int j = 1;j<cols; j++ ){
//                Cell cell = row.getCell(j);
//                String key = keysRow.getCell(j).getStringCellValue();
//                switch (cell.getCellType()){
//                    case NUMERIC:{
//                        map.put(key,cell.getNumericCellValue());
//                        break;
//                    }
//                    case STRING:{
//                        map.put(key,cell.getStringCellValue());
//                        break;
//                    }
//                    case _NONE:{
//                        break;
//                    }
//                }
//            }
//            datas.add(map);
//        }
//        return datas;
//    }
//
//
//    //添加边框公共部分
//    private static void addCellBorder(Workbook workbook,Cell cell){
//        CellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setBorderBottom(BorderStyle.THIN);
//        cellStyle.setBorderTop(BorderStyle.THIN);
//        cellStyle.setBorderLeft(BorderStyle.THIN);
//        cellStyle.setBorderRight(BorderStyle.THIN);
//        cell.setCellStyle(cellStyle);
//    }
//
//}

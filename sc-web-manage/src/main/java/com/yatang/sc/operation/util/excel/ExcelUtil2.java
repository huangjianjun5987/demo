package com.yatang.sc.operation.util.excel;

import com.yatang.sc.common.utils.excel.ExcelFieldName;
import com.yatang.sc.common.utils.excel.ExcelName;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by xiangyonghong on 2017/7/19.
 */
public class ExcelUtil2 {

    /**
     * 导出excel
     * @param list
     * @param response
     * @throws Exception
     */
    public static void listToExcel(List list, HttpServletResponse response) throws Exception{
        if(list.size()<=0){
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("<script>alert('当前条件下无记录。')</script>");
            response.getWriter().flush();
            return;
        }
        String name = getExcelName(list.get(0));
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        name += df.format(new Date());
        response.setContentType("application/x-excel");
        response.setHeader("Content-Disposition", "attachment;filename="+ name +".xls");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ServletOutputStream out = response.getOutputStream();
        WritableWorkbook book = Workbook.createWorkbook(out);
        WritableSheet sheet = null;
        sheet = book.createSheet(name, 0);
        List<String> title = getExcelTitle(list.get(0));

        for (int j = 0; j < title.size(); j++) {
            sheet.addCell(new Label(j, 0, title.get(j).split("-")[1]));
        }
        for (int i = 0; i < list.size(); i++) {
            BeanWrapper bw = new BeanWrapperImpl(list.get(i));
            for(int h=0;h<title.size();h++){
                String propertyValue ="";
                Object object = bw.getPropertyValue(title.get(h).split("-")[0]);
                if(object !=null){
                    if(object instanceof Date){
                        propertyValue = sf.format((Date) object);
                    }else{
                        propertyValue = object.toString();
                    }
                    if(object instanceof Double && Double.valueOf(object.toString())==0){
                        propertyValue = "";
                    }
                }
                sheet.addCell(new Label(h, i+1, propertyValue));
            }
        }


        book.write();
        book.close();
        out.flush();
        out.close();
    }

    public static List<String> getExcelTitle(Object t){
        Field[] fields = t.getClass().getDeclaredFields();
        List<String> list = new ArrayList<>();
        for (Field field:fields){
            if(field.getAnnotation(ExcelFieldName.class)!=null){
                list.add(field.getName()+"-"+field.getAnnotation(ExcelFieldName.class).name());
            }
        }
        return list;
    }

    public static String getExcelName(Object t){
        if(t.getClass().getAnnotation(ExcelName.class)!=null){
            return t.getClass().getAnnotation(ExcelName.class).name();
        }
        return "";
    }

    /**
     * 导出Excel模板
     * @param list
     * @param response
     * @throws Exception
     */
    public static void listToExcel2(List list, HttpServletResponse response) throws Exception{
        if(list.size()<=0){
            return ;
        }
        String name = getExcelName(list.get(0));
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        name += df.format(new Date());
        response.setContentType("application/x-excel");
        response.setHeader("Content-Disposition", "attachment;filename="+ name +".xls");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ServletOutputStream out = response.getOutputStream();
        WritableWorkbook book = Workbook.createWorkbook(out);
        WritableSheet sheet = null;
        sheet = book.createSheet(name, 0);
        List<String> title = getExcelTitle(list.get(0));

        for (int j = 0; j < title.size(); j++) {
            sheet.addCell(new Label(j, 0, title.get(j).split("-")[1]));
        }
        for (int i = 0; i < list.size(); i++) {
            if(i==0){
                continue;
            }
            BeanWrapper bw = new BeanWrapperImpl(list.get(i));
            for(int h=0;h<title.size();h++){
                String propertyValue ="";
                Object object = bw.getPropertyValue(title.get(h).split("-")[0]);
                if(object !=null){
                    if(object instanceof Date){
                        propertyValue = sf.format((Date) object);
                    }else{
                        propertyValue = object.toString();
                    }
                    if(object instanceof Double && Double.valueOf(object.toString())==0){
                        propertyValue = "";
                    }
                }
                sheet.addCell(new Label(h, i+1, propertyValue));
            }
        }


        book.write();
        book.close();
        out.flush();
        out.close();
    }
}

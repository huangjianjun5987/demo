package com.yatang.sc.operation.util;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/19.
 */
public class ExcelUtil {

    /**
     *
     * @param name excel名称
     * @param title 表头，格式为property-字段名，property为对象属性名
     * @param list 数据
     * @param response 响应
     * @throws Exception
     */
    public static void listToExcel(String name, String[] title, List list, HttpServletResponse response) throws Exception{

        response.setContentType("application/x-excel");
        response.setHeader("Content-Disposition", "attachment;filename="+ name +".xls");
        ServletOutputStream out = response.getOutputStream();
        WritableWorkbook book = Workbook.createWorkbook(out);
        WritableSheet sheet = null;
        sheet = book.createSheet(name, 0);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int j = 0; j < title.length; j++) {
            sheet.addCell(new Label(j, 0, title[j].split("-")[1]));
        }
        for (int i = 0; i < list.size(); i++) {
            BeanWrapper bw = new BeanWrapperImpl(list.get(i));
            for(int h=0;h<title.length;h++){
                String propertyValue ="";
                Object object = bw.getPropertyValue(title[h].split("-")[0]);
                if(object !=null){
                    if(object instanceof Date){
                        propertyValue = sf.format((Date) object);
                    }else{
                        propertyValue = object.toString();
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

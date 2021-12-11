package com.atguigu.eduservice.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class excelTest {
    @Test
    public void writeTest(){

        // 1.数据存储到哪儿
        String fileName = "D:\\testEasyExcel\\write.xlsx";
        EasyExcel.write(fileName,DemoData.class).sheet("学生列表").doWrite(data());
    }

    @Test
    public void readTest(){
        // 1.从哪儿读
        String fileName = "D:\\testEasyExcel\\write.xlsx";
        // 2.说明：从"D:\\testEasyExcel\\write.xlsx"读，读取完在 ExcelListener 中操作，默认读第一个sheet
        EasyExcel.read(fileName, DemoData.class,new ExcelListener()).sheet().doRead();
    }


    //循环设置要添加的数据，最终封装到list集合中
   public static List<DemoData> data(){
        List<DemoData> list=new ArrayList<>();
       for (int i = 0; i <15 ; i++) {
           DemoData data=new DemoData();
           data.setSno(i);;
           data.setSname("韦宝家"+i);
           list.add(data);
       }
       return list;
   }
}

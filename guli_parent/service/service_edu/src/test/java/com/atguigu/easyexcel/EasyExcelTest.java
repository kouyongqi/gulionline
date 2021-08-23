package com.atguigu.easyexcel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;

public class EasyExcelTest {

    @Test
    public void test(){
        ArrayList<DataDemo> students = new ArrayList();

        for (int i=1;i<6;i++){
            DataDemo dataDemo = new DataDemo();
            dataDemo.setSno(i);
            dataDemo.setSname("kou"+i);
            students.add(dataDemo);
        }
        EasyExcel.write("E:/write.xlsx",DataDemo.class).sheet("学生列表").doWrite(students);
    }

    @Test
    public void test1(){
       EasyExcel.read("E:/write.xlsx",DataDemo.class,new EasyExcelListener()).sheet().doRead();
    }

}

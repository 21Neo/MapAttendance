package com.attendance.controller;

import com.attendance.pojo.UserAttendance;
import com.attendance.service.AttendanceService;
import com.attendance.util.SortUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/Excel")
public class ExcelController{

    @Autowired
    private AttendanceService attendanceService;

    // 查询所有的考勤记录
    @RequestMapping(value = "/attendances",method = RequestMethod.GET)
    public ResponseEntity getAtt(
            @RequestParam(value = "aDept",required = false) String aDept,
            @RequestParam(value = "beginTime",required = false)
            @DateTimeFormat(pattern = "yyyy-MM") Date beginTime
            ){
        System.out.println("日期："+beginTime);
        // 处理时间
        if(beginTime != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String begin = String.valueOf(beginTime+"-01");
            System.out.println("处理后的时间为："+begin);
            try {
                beginTime = sdf.parse(begin);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        List<UserAttendance> attendances = attendanceService.findAll(aDept,beginTime);
        return new ResponseEntity(attendances,HttpStatus.OK);
    }

    // 下载
    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public ResponseEntity<?> download(
        @RequestParam(value = "aDept",required = false) String aDept,
        @RequestParam(value = "beginTime",required = false)
        @DateTimeFormat(pattern = "yyyy-MM") Date beginTime
    ) throws IOException {
        // 处理时间
        if(beginTime != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String begin = String.valueOf(beginTime+"-01");
            System.out.println(begin);
            try {
                beginTime = sdf.parse(begin);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        List<UserAttendance> attendances = attendanceService.findAll(aDept,beginTime);
        for(UserAttendance attendance:attendances){
            System.out.println("姓名："+attendance.getAuserName());
        }
        // 生成excel表格
        HSSFWorkbook workbook = attendanceService.exportExcel(attendances);
        // 将excel写入输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        outputStream.close();
        // 设置输出格式
        HttpHeaders headers = new HttpHeaders();
        String fileName = new String("考勤报表.xls".getBytes("UTF-8"), "iso-8859-1");
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.CREATED);
    }

    // 生成图标
    @RequestMapping(value = "/createPic",method = RequestMethod.GET)
    public ResponseEntity<?> createPic(
            @RequestParam(value = "aDept",required = false) String aDept,
            @RequestParam(value = "beginTime",required = false)
            @DateTimeFormat(pattern = "yyyy-MM") Date beginTime
    ) throws IOException {
        // 处理时间
        if(beginTime != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String begin = String.valueOf(beginTime+"-01");
            System.out.println(begin);
            try {
                beginTime = sdf.parse(begin);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        List<UserAttendance> attendances = attendanceService.findAll(aDept,beginTime);
        // 将同一个人的信息存进同一个集合中
        // 1.进行排序
        SortUtils.sortwith(attendances);
        for(UserAttendance attendance:attendances){
            System.out.println(attendance.getAuserName());
        }
        // 将同一个人的信息存进一个集合中
        String firstP = "";
        Map<String,Integer> map = new HashMap<>();
        int j=1;
        /*map.put(firstP,j);*/
        for(int i=0;i<attendances.size();i++){
            if(firstP.equals(attendances.get(i).getAuserName())){
                // 这个姓名的条数+1
                map.put(firstP,j++);
            }else{
                j=1;
                map.put(attendances.get(i).getAuserName(),j++);
                firstP = attendances.get(i).getAuserName();
            }
        }
        // 循环输出map集合中的数
        Set keySet = map.keySet();
        Iterator it = keySet.iterator();
        while (it.hasNext()){
            Object k = it.next();
            Object v = map.get(k);
            System.out.println("k = "+k+" v = "+v);
        }

//        System.out.println("map ："+map.get(0).get(0));
        // 查询出这个部分每个人的考勤打卡记录数，比如黎明是4条
        return new ResponseEntity<>(map,HttpStatus.CREATED);
    }
}

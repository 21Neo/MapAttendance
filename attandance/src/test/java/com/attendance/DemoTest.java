package com.attendance;

import com.attendance.pojo.UserAttendance;
import com.attendance.service.AttendanceService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DemoTest {
    private AttendanceService attendanceService;

    @Before
    public void init(){
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        attendanceService = ctx.getBean("attendanceService",AttendanceService.class);
    }

    @Test
    public void test(){
       /* String adept = "财务部";*/
        Date beginTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            beginTime = sdf.parse("2018-3-27");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<UserAttendance> users = attendanceService.findAll("财务部",beginTime);
        for(UserAttendance s:users){
            System.out.println(s.getAuserName());
        }
    }
}

package com.attendance;

import com.attendance.pojo.AskForLeave;
import com.attendance.service.AskForLeaveService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AskforleaveTest {
    private AskForLeaveService askForLeaveService;
    @Test
    public void findById(){
        AskForLeave leave = askForLeaveService.findAflById(2);
        System.out.println(leave.getUserName());
    }
    @Before
    public void init() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        askForLeaveService = ctx.getBean("askForLeaveService",AskForLeaveService.class);
    }
}

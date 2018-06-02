package com.attendance;

import com.attendance.pojo.Travel;
import com.attendance.service.TravelService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class TravelTest {
    private TravelService travelService;
    @Test
    public void add(){
        Travel travel = new Travel();
        travel.setTravelUserID(1);
        travel.setTravelUserName("admin");
        travel.setTravelDate(new Date());
        travel.setTravelDays(7d);
        travel.setAddress("beijing");
        travel.setCost(6000d);
        travel.setTstep(1);
        travel.setRemark("test");
        int count = travelService.addTravel(travel);
        System.out.println(count);
    }
    @Test
    public void findSelf(){
        for (Travel travel : travelService.findSelfTravels(1,null,null)){
            System.out.println(travel.getAddress()+" "+travel.getTdept());
        }
    }
    @Before
    public void init(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        travelService = ctx.getBean("travelService",TravelService.class);
    }
}

package com.attendance;

import com.attendance.pojo.ReissueCard;
import com.attendance.service.ReissueCardService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class RCardTest {
    private ReissueCardService reissueCardService;
    @Test
    public void add(){
        ReissueCard reissueCard = new ReissueCard();
        reissueCard.setrUserID(1);
        reissueCard.setrUserName("admin");
        reissueCard.setrDept("HR");
        reissueCard.setrDate(new Date());
        reissueCard.setrType("A");//A上班补卡 B下班补卡
        reissueCard.setrStep(1);
        int count = reissueCardService.addRCard(reissueCard);
        System.out.println(count);
    }
    @Test
    public void find(){
        for (ReissueCard reissueCard : reissueCardService.findSelfRC(1,null,null)){
            System.out.println(reissueCard.getrUserName());
        }
    }
    @Before
    public void init(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        reissueCardService = ctx.getBean("reissueCardService",ReissueCardService.class);
    }
}

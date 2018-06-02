package com.attendance.controller;

import com.attendance.pojo.Scheduling;
import com.attendance.pojo.UserAttendance;
import com.attendance.pojo.UserInfo;
import com.attendance.service.AttendanceService;
import com.attendance.service.SchedulingService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/att")
public class AttendanceController {

    @Autowired
    private AttendanceService service;
    @Autowired
    private SchedulingService schedulingService;

    // 申请修改考勤异常信息
    @RequestMapping(value = "/modifyExe",method = RequestMethod.POST)
    public ResponseEntity modify(
            @RequestParam(value = "userID",required = false) Integer userID,
            @RequestParam(value = "attRemark",required = false) String attRemark,
            @RequestParam(value = "nowTime",required = false) String nowTime
            ){
        System.out.println("userID = "+userID+" attRemark="+attRemark+" nowTime="+nowTime);
        CustomType customType = new CustomType(400,"申请失败");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date no = sdf.parse(nowTime);
            int count = service.modify(userID,attRemark,no);
            System.out.println("count = "+count);
            if(count>0){
                customType = new CustomType(200,"申请成功");
                System.out.println("成功了？？？");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(customType,HttpStatus.OK);
    }

    // 查询班次信息表
    @RequestMapping(value = "/scheduling",method = RequestMethod.GET)
    public ResponseEntity findSche(){
        return new ResponseEntity(schedulingService.findSch(),HttpStatus.OK);
    }

    /*// 查询所有的考勤记录
   @RequestMapping(value = "/selectAll",method = RequestMethod.GET)
   public ResponseEntity<?> find(){
       System.out.println("你好。。。。");
       CustomType customType = new CustomType(400,"查询错误");
//       UserInfo attendance = (UserInfo) session.getAttribute("userInfo");
//       System.out.println("记录的session="+attendance);
       String aDept = null;
       List<UserAttendance> attendances = service.find(aDept);
       return new ResponseEntity<>(attendances,HttpStatus.OK);
   }*/

   // 查询当天的打卡记录
    @RequestMapping(value = "/findNow",method = RequestMethod.GET)
    public ResponseEntity findNow(HttpSession session){
        System.out.println("查询今天打卡。。。。");
       // 获取session中的aUserID
        UserInfo attendance = (UserInfo) session.getAttribute("userInfo");
//        System.out.println("attendance = "+attendance.getUserID());
        UserAttendance select = service.find(attendance.getUserID(),new Date());
        System.out.println("select = "+select.getBeginTime());
        return new ResponseEntity(select,HttpStatus.OK);
    }

   // 模糊查询
    @RequestMapping(value = "/selectByParam",method = RequestMethod.GET)
    public ResponseEntity<?> find(
            @Param("beforeTime")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date beforeTime,
            @Param("afterTime")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date afterTime,
            HttpSession session
    ){
        System.out.println("模糊查询。。。。");
        System.out.println(beforeTime+" "+afterTime);
        CustomType customType = new CustomType(400,"查询错误");
        if(beforeTime != null && afterTime != null) {
            // 截止日期不能在开始日期之前
            if (afterTime.before(beforeTime)) {
                return new ResponseEntity<>(new CustomType(400, "截止日期不能在开始日期之前!"), HttpStatus.OK);
            }
            // 截止日期不能在当前日期之后
            if (afterTime.after(new Date())) {
                return new ResponseEntity<>(new CustomType(400, "截止日期不能在开始日期之前!"), HttpStatus.OK);
            }
            // 把指定日期往后增加一天;负数则减
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(afterTime);
            calendar.add(Calendar.DATE, 1);
            afterTime = calendar.getTime();
        }
        UserInfo attendance = (UserInfo) session.getAttribute("userInfo");
        List<UserAttendance> attendances = service.find(attendance.getUserID(),beforeTime,afterTime);
        return new ResponseEntity<>(attendances,HttpStatus.OK);
    }


    // 打卡
    @RequestMapping(value = "modifyAtt",method = RequestMethod.POST)
    public ResponseEntity punch(
            @RequestParam("username") String username,
            @RequestParam("dept") String dept,
            @RequestParam("distance") Double distance,
            @RequestParam("aUserID") Integer aUserID
            ){
        CustomType customType = new CustomType(400,"打卡失败");
        System.out.println("com on...");
        System.out.println("aUserID = "+aUserID);
        // 首先判断是上班卡 还是下班卡
        UserAttendance attendance = service.findByTime(new Date(),username);
        // 新记录
        UserAttendance newinfo = new UserAttendance();
        System.out.println(attendance);
//        System.out.println("用户名："+attendance.getAuserName());
        if(attendance == null){
            // 上班卡
            System.out.println("上班。。。。");
            SimpleDateFormat sdf = new SimpleDateFormat("HH");
            Integer h = Integer.parseInt(sdf.format(new Date()));
            customType.setCode(100);
            customType.setMessage("上班卡成功");
            newinfo.setAuserName(username);
            newinfo.setDistance(distance);
            newinfo.setaDept(dept);
            newinfo.setaUserID(aUserID);
            int count = service.addAttenDance(newinfo);
            System.out.println("count = "+count);
        }else{
            // 下班卡
            System.out.println("下班。。。。");
            customType.setCode(200);
            customType.setMessage("下班卡成功");
            newinfo.setAuserName(username);
            newinfo.setDistance(distance);
            newinfo.setaDept(dept);
            newinfo.setaID(attendance.getaID());
            int count = service.modifyAttendance(newinfo);
        }
        return new ResponseEntity(customType, HttpStatus.OK);
    }

}

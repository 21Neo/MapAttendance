package com.attendance.service;

import com.attendance.controller.CustomType;
import com.attendance.dao.AttendanceMapper;
import com.attendance.dao.PermissionMapper;
import com.attendance.dao.SchedulingMapper;
import com.attendance.dao.UserInfoMapper;
import com.attendance.pojo.Permission;
import com.attendance.pojo.Scheduling;
import com.attendance.pojo.UserAttendance;
import com.attendance.util.ExcelUtils;
import com.attendance.util.ExcelUtils2;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true,propagation = Propagation.NOT_SUPPORTED)
public class AttendanceService {

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Autowired
    private SchedulingMapper schedulingMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    // 查询今天的打卡记录
    public UserAttendance find(Integer aUserID,Date nowTime){
        return attendanceMapper.findNow(aUserID,nowTime);
    }

    // 修改打卡记录
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int modify(Integer aUserID,String attRemark,Date nowTime){
        return attendanceMapper.modify(aUserID,attRemark,nowTime);
    }

    // 查询本人的打卡记录
    public List<UserAttendance> find(Integer aUserID){
        return attendanceMapper.findById(aUserID);
    }

    // 模糊查询
    public List<UserAttendance> find(Integer aUserID,Date beforeTime,Date afterTime){
        return attendanceMapper.findByParam(aUserID,beforeTime,afterTime);
    }

    // 查询所有的打卡记录
    public List<UserAttendance> findAll(String aDept,Date beginTime){
        return attendanceMapper.findAll(aDept,beginTime);
    }

    // 判断是否打卡
    public UserAttendance findByTime(Date beginTime,String useID){
        return attendanceMapper.findByTime(beginTime,useID);
    }

    // 上班
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int addAttenDance(UserAttendance userAttendance){
        userAttendance.setBeginTime(new Date());
        // 1.判断打卡的时间 决定 amType 的类型
        System.out.println(userAttendance.getBeginTime());
        Date beginTime = userAttendance.getBeginTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String sbeginTime = sdf.format(beginTime);
        System.out.println("sss = "+sbeginTime);
        // 获取班次表的数据
        Scheduling scheduling = schedulingMapper.findType("A");
        String startTime = scheduling.getsStartTime();

        // 获取班次表的数据
        Scheduling scheduling1 = schedulingMapper.findType("B");
        String end = scheduling.getsEndTime();
        // 下午小时处理
        Integer ph = Integer.parseInt(end.substring(0,2));
        // 小时处理
        Integer hours = Integer.parseInt(sbeginTime.substring(0,2));
        Integer h = Integer.parseInt(startTime.substring(0,2));
        // 分钟处理
        Integer minutes = Integer.parseInt(sbeginTime.substring(3,5));
        Integer m = Integer.parseInt(startTime.substring(3,5));
        // 秒处理
        Integer seconds = Integer.parseInt(sbeginTime.substring(6,8));
        Integer s = Integer.parseInt(startTime.substring(6,8));
        System.out.println("系统时间："+hours+" "+minutes+" "+seconds);
        System.out.println("对比时间："+h+" "+m+" "+s);
        // 开始日期的处理
        if(hours<h){
           userAttendance.setAmType("Y");
            System.out.println("hhh1 = "+hours);
        }else if(hours==h && minutes==0 && seconds==0){
            userAttendance.setAmType("Y");
            System.out.println("hhh = "+hours);
        }else if(hours==h && minutes<=60){
            // 迟到
            userAttendance.setAmType("N");
        }else if(hours>=h+1){
            userAttendance.setAmType("T");
        }
        return attendanceMapper.addAttenDance(userAttendance);
    }

    // 下班
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int modifyAttendance(UserAttendance userAttendance){
        // 判断打卡的时间 决定 pmType 的类型
        userAttendance.setEndTime(new Date());
        // 1.判断打卡的时间 决定 amType 的类型
        System.out.println(userAttendance.getEndTime());
        Date endTime = userAttendance.getEndTime();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        String sendTime = sdf.format(endTime);
        System.out.println("sss = "+sendTime);
        // 获取班次表的数据
        Scheduling scheduling = schedulingMapper.findType("B");
        String end = scheduling.getsEndTime();
        // 小时处理
        Integer hours = Integer.parseInt(sendTime.substring(0,2));
        Integer h = Integer.parseInt(end.substring(0,2));
        // 分钟处理
        Integer minutes = Integer.parseInt(sendTime.substring(3,5));
        Integer m = Integer.parseInt(end.substring(3,5));
        // 秒处理
        Integer seconds = Integer.parseInt(sendTime.substring(6,8));
        Integer s = Integer.parseInt(end.substring(6,8));
        System.out.println("系统时间："+hours+" "+minutes+" "+seconds);
        System.out.println("对比时间："+h+" "+m+" "+s);
        // 开始日期的处理
        if(hours<h){
            userAttendance.setPmType("L");
        }else if(hours>=h){
            userAttendance.setPmType("Y");
        }
        return attendanceMapper.modifyAttendance(userAttendance);
    }

    /**
     * 打卡记录
     * @param
     * @return
     *//*
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int add(UserAttendance attendance){
        return attendanceMapper.addAttenDance(attendance);
    }*/

    public void download(
            List<UserAttendance> attendances,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String[] rowsName = new String[]{"序号", "车名", "价格", "生产日期", "图片路径"};
        List<Object[]> dataList = new ArrayList<>();
        for (int i = 0; i < attendances.size(); i++) {
            Object[] objects = new Object[rowsName.length];
            UserAttendance atts = attendances.get(i);
            objects[0] = i + 1;
            objects[1] = atts.getaUserID();
            objects[2] = atts.getAuserName();
            objects[3] = atts.getBeginTime();
            objects[4] = atts.getAmType();
            objects[5] = atts.getEndTime();
            objects[6] = atts.getPmType();
            objects[7] = atts.getDistance();
            objects[8] = atts.getaDept();
            objects[9] = atts.getAttRemark();
            dataList.add(objects);
        }
        ExcelUtils.exportExcel("考勤报表",
                rowsName,
                dataList,
                "yyyy-MM-dd",
                request,
                response);
    }

    public HSSFWorkbook exportExcel(List<UserAttendance> attendances) throws IOException {
        String[] rowsName = new String[]{"序号", "用户Id", "用户名", "上班打卡", "状态","下班打卡","状态","距离","部门","备注"};
        List<Object[]> dataList = new ArrayList<>();
        for (int i = 0; i < attendances.size(); i++) {
            Object[] objects = new Object[rowsName.length];
            UserAttendance atts = attendances.get(i);
            objects[0] = i + 1;
            objects[1] = atts.getaUserID();
            objects[2] = atts.getAuserName();
            objects[3] = atts.getBeginTime();
            objects[4] = atts.getAmType();
            objects[5] = atts.getEndTime();
            objects[6] = atts.getPmType();
            objects[7] = atts.getDistance();
            objects[8] = atts.getaDept();
            objects[9] = atts.getAttRemark();
            dataList.add(objects);
        }
        return ExcelUtils2.exportExcel("考勤报表",
                rowsName,
                dataList,
                "yyyy-MM-dd");
    }
}

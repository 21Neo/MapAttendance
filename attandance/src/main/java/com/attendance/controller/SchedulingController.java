package com.attendance.controller;

import com.attendance.pojo.Scheduling;
import com.attendance.service.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 班次管理
 */
@RestController
@RequestMapping("/sche")
public class SchedulingController {
    @Autowired
    private SchedulingService service;

    // 查询所有班次
    @RequestMapping(value = "/find",method = RequestMethod.GET)
    public ResponseEntity find(HttpSession session){
        CustomType customType = new CustomType(400,"你没有此权限!");
        String myrights = (String) session.getAttribute("rights");
        if(myrights == null){
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
        Set<String> smyrights = new HashSet<>();
        String[] mmyrights = myrights.split(",");
        for (String s : mmyrights) {
            smyrights.add(s);//权限分别放入set
        }
        if(!smyrights.contains("scheduling")){
            customType.setMessage("你没有此权限!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        } else {
            List<Scheduling> scheduling = service.findSch();
            return new ResponseEntity(scheduling, HttpStatus.OK);
        }
    }

    // 修改班次
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResponseEntity update(
            @RequestParam("sStartTime") String sStartTime,
            @RequestParam("sEndTime") String sEndTime,
            @RequestParam("sID") Integer sID,HttpSession session
    ){
        CustomType customType = new CustomType(400,"你没有此权限!");
        String myrights = (String) session.getAttribute("rights");
        if(myrights == null){
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
        Set<String> smyrights = new HashSet<>();
        String[] mmyrights = myrights.split(",");
        for (String s : mmyrights) {
            smyrights.add(s);//权限分别放入set
        }
        if(!smyrights.contains("scheduling")){
            customType.setMessage("你没有此权限!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        } else {
            System.out.println("sStartTime = " + sStartTime + " sEndTime=" + sEndTime + " sID=" + sID);
            customType.setCode(400);
            customType.setMessage("修改失败");
            Scheduling scheduling = new Scheduling();
            // 获得今天的日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String startTime = sdf.format(new Date());
            System.out.println("startTime = " + startTime);
            scheduling.setsStartTime(startTime + " " + sStartTime);
            scheduling.setsID(sID);
            scheduling.setsEndTime(startTime + " " + sEndTime);
            int count = service.modify(scheduling);
            if (count > 0) {
                System.out.println("成功----------------");
                // 修改成功
                customType.setCode(200);
                customType.setMessage("成功");
            }
            return new ResponseEntity(customType, HttpStatus.OK);
        }
    }

    // 根据 SType 查询班次
    @RequestMapping(value = "/findByType",method = RequestMethod.GET)
    public ResponseEntity finddByType(@RequestParam("idvalue") String idvalue,HttpSession session){
        CustomType customType = new CustomType(400,"你没有此权限!");
        String myrights = (String) session.getAttribute("rights");
        if(myrights == null){
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
        Set<String> smyrights = new HashSet<>();
        String[] mmyrights = myrights.split(",");
        for (String s : mmyrights) {
            smyrights.add(s);//权限分别放入set
        }
        if(!smyrights.contains("scheduling")){
            customType.setMessage("你没有此权限!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        } else {
            Scheduling sche = service.find(idvalue);
            return new ResponseEntity(sche, HttpStatus.OK);
        }
    }
}

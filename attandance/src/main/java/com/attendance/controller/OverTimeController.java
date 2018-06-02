package com.attendance.controller;

import com.attendance.pojo.OverTime;
import com.attendance.pojo.UserInfo;
import com.attendance.service.OverTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class OverTimeController {
    @Autowired
    private OverTimeService overTimeService;

    @RequestMapping(value = "/newOvt", method = RequestMethod.POST)
    public ResponseEntity<?> newOverT(
            @RequestParam("overTimeDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date overTimeDate,
            @RequestParam("hours") Double hours,
            @RequestParam("reason") String reason, HttpSession session) {
        CustomType customType = new CustomType(400, "保存失败!");
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (userInfo == null) {
            customType.setMessage("请先登录!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
        OverTime overTime = new OverTime();
        overTime.setoUserID(userInfo.getUserID());
        overTime.setoUserName(userInfo.getUsername());
        overTime.setOverTimeDate(overTimeDate);
        overTime.setHours(hours);
        overTime.setOdept(userInfo.getDept());
        overTime.setOstep(1);//提出申请
        overTime.setReason(reason);
        int count = overTimeService.addOverT(overTime);
        if (count > 0) {
            customType.setCode(200);
            customType.setMessage("保存成功!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
        return new ResponseEntity<>(customType, HttpStatus.OK);
    }

    @RequestMapping(value = "/findSelfOvt", method = RequestMethod.POST)
    public ResponseEntity<?> findSelf(
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(value = "beginDate", required = false) Date beginDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(value = "endDate", required = false) Date endDate, HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        CustomType customType = new CustomType(400, "请先登录!");
        if (userInfo == null) {
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
        List<OverTime> list = overTimeService.findSelf(userInfo.getUserID(), beginDate, endDate);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/saveOvt",method = RequestMethod.POST)
    public ResponseEntity<?> saveAfl(@RequestParam("oid") Integer oid,
                                     @RequestParam(value = "ochargeName",required = false) String ochargeName,
                                     @RequestParam(value = "ochargeComment",required = false) String ochargeComment,
                                     @RequestParam(value = "ohrName",required = false) String ohrName,
                                     @RequestParam(value = "ohrComment",required = false) String ohrComment,
                                     HttpSession session){
        CustomType customType = new CustomType(400, "保存失败!");
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        String myrights = (String) session.getAttribute("rights");
        if(myrights == null){
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
        Set<String> smyrights = new HashSet<>();
        String[] mmyrights = myrights.split(",");
        for (String s : mmyrights) {
            smyrights.add(s);//权限分别放入set
        }
        if(!smyrights.contains("overtime")){
            customType.setMessage("你没有此权限!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        } else {
            OverTime overTime = overTimeService.findOvtById(oid);
            int count = 0;
            if (overTime.getOstep() == 1) {//部门审核
                overTime.setOchargeName(ochargeName);
                overTime.setOchargeComment(ochargeComment);
                overTime.setOchargeDate(new Date());
                if (ochargeComment.equals("同意")) {
                    overTime.setOstep(2);
                } else {
                    overTime.setOstep(4);
                }
                count = overTimeService.updateOvt(overTime);
                if (count > 0) {
                    customType.setCode(200);
                    customType.setMessage("保存成功!");
                }
                return new ResponseEntity<>(customType, HttpStatus.OK);
            } else if (overTime.getOstep() == 2) {//HR审核
                overTime.setOhrName(userInfo.getUsername());
                overTime.setOhrComment(ochargeComment);
                overTime.setOhrDate(new Date());
                if (ochargeComment.equals("同意")) {
                    overTime.setOstep(3);
                } else {
                    overTime.setOstep(4);
                }
                count = overTimeService.updateOvt(overTime);
                if (count > 0) {
                    customType.setCode(200);
                    customType.setMessage("保存成功!");
                }
                return new ResponseEntity<>(customType, HttpStatus.OK);
            }
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/findoChargeOvt",method = RequestMethod.POST)
    public ResponseEntity<?> findChargeandHr(
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(value = "beginDate", required = false) Date beginDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(value = "endDate", required = false) Date endDate, HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        CustomType customType = new CustomType(400, "请先登录!");
        if (userInfo == null) {
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
        String myrights = (String) session.getAttribute("rights");
        if(myrights == null){
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
        Set<String> smyrights = new HashSet<>();
        String[] mmyrights = myrights.split(",");
        for (String s : mmyrights) {
            smyrights.add(s);//权限分别放入set
        }
        if(!smyrights.contains("overtime")){
            customType.setMessage("你没有此权限!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        } else {
            List<OverTime> list = null;
            if (userInfo.getIsmanager().equals("y") && !userInfo.getDept().equals("HR")) {
                //本部门所有申请
                list = overTimeService.findChargeovt(userInfo.getDept(), beginDate, endDate);
            } else if (userInfo.getIsmanager().equals("y") && userInfo.getDept().equals("HR")) {//需处理HR部门非经理的申请
                //所有部门申请和hr审批
                list = overTimeService.findHrovt(beginDate, endDate);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/findOvtById",method = RequestMethod.GET)
    public ResponseEntity<?> findAflById(@RequestParam("oid") Integer oid,HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        OverTime overTime =overTimeService.findOvtById(oid);
        if(overTime.getOchargeName() == null && overTime.getOhrName() == null) {
            overTime.setOchargeName(userInfo.getUsername());
        } else if(overTime.getOchargeName()!=null && overTime.getOhrName() == null){
            overTime.setOhrName(userInfo.getUsername());
        }
        return new ResponseEntity<>(overTime,HttpStatus.OK);
    }

}

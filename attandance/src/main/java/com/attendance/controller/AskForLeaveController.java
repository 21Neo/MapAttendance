package com.attendance.controller;

import com.attendance.pojo.AskForLeave;
import com.attendance.pojo.UserInfo;
import com.attendance.service.AskForLeaveService;
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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class AskForLeaveController {
    @Autowired
    private AskForLeaveService askForLeaveService;

    @RequestMapping(value = "/findSelfAfl", method = RequestMethod.POST)
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
        List<AskForLeave> list = askForLeaveService.findSelf(userInfo.getUserID(), beginDate, endDate);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/findChargeAfl",method = RequestMethod.POST)
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
        if(!smyrights.contains("afl")){
            customType.setMessage("你没有此权限!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        } else {
            List<AskForLeave> list = null;
            if (userInfo.getIsmanager().equals("y") && !userInfo.getDept().equals("HR")) {
                //本部门所有申请
                list = askForLeaveService.findChargeafl(userInfo.getDept(), beginDate, endDate);
            } else if (userInfo.getIsmanager().equals("y") && userInfo.getDept().equals("HR")) {//需处理HR部门非经理的申请
                //所有部门申请和hr审批
                list = askForLeaveService.findHrafl(beginDate, endDate);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/findAflById",method = RequestMethod.GET)
    public ResponseEntity<?> findAflById(@RequestParam("askid") Integer askid,HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        AskForLeave leave = askForLeaveService.findAflById(askid);
        leave.setChargeName(userInfo.getUsername());
        return new ResponseEntity<>(leave,HttpStatus.OK);
    }

    @RequestMapping(value = "/saveAfl",method = RequestMethod.POST)
    public ResponseEntity<?> saveAfl(@RequestParam("askid") Integer askid,
                                     @RequestParam(value = "chargeName",required = false) String chargeName,
                                     @RequestParam(value = "chargeComment",required = false) String chargeComment,
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
        if(!smyrights.contains("afl")){
            customType.setMessage("你没有此权限!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        } else {
            AskForLeave askForLeave = askForLeaveService.findAflById(askid);
            int count = 0;
            if (askForLeave.getStep() == 1) {//部门审核
                askForLeave.setChargeName(chargeName);
                askForLeave.setChargeComment(chargeComment);
                askForLeave.setChargeDate(new Date());
                if (chargeComment.equals("同意")) {
                    askForLeave.setStep(2);
                } else {
                    askForLeave.setStep(4);
                }
                count = askForLeaveService.updateAfl(askForLeave);
                if (count > 0) {
                    customType.setCode(200);
                    customType.setMessage("保存成功!");
                }
                return new ResponseEntity<>(customType, HttpStatus.OK);
            } else if (askForLeave.getStep() == 2) {//HR审核
                askForLeave.setHrName(userInfo.getUsername());
                askForLeave.setHrComment(chargeComment);
                askForLeave.setHrDate(new Date());
                if (chargeComment.equals("同意")) {
                    askForLeave.setStep(3);
                } else {
                    askForLeave.setStep(4);
                }
                count = askForLeaveService.updateAfl(askForLeave);
                if (count > 0) {
                    customType.setCode(200);
                    customType.setMessage("保存成功!");
                }
                return new ResponseEntity<>(customType, HttpStatus.OK);
            }
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/newAfl", method = RequestMethod.POST)
    public ResponseEntity<?> newAfl(
            @RequestParam("entryDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date entryDate,
            @RequestParam("days") Double days,
            @RequestParam("remark") String remark, HttpSession session) {
        CustomType customType = new CustomType(400, "保存失败!");
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (userInfo == null) {
            customType.setMessage("请先登录!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
        AskForLeave askForLeave = new AskForLeave();
        askForLeave.setUserid(userInfo.getUserID());
        askForLeave.setUserName(userInfo.getUsername());
        askForLeave.setEntryDate(entryDate);
        askForLeave.setDays(days);
        askForLeave.setAskDept(userInfo.getDept());
        askForLeave.setStep(1);//提出申请
        askForLeave.setRemark(remark);
        int count = askForLeaveService.addAfl(askForLeave);
        if (count > 0) {
            customType.setCode(200);
            customType.setMessage("保存成功!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
        return new ResponseEntity<>(customType, HttpStatus.OK);
    }
}

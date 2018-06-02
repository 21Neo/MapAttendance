package com.attendance.controller;

import com.attendance.pojo.ReissueCard;
import com.attendance.pojo.UserInfo;
import com.attendance.service.ReissueCardService;
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
public class ReissueCardController {
    @Autowired
    private ReissueCardService reissueCardService;

    @RequestMapping(value = "/findSelfRC",method = RequestMethod.POST)
    public ResponseEntity<?> findSelfRC(
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(value = "beginDate", required = false) Date beginDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(value = "endDate", required = false) Date endDate, HttpSession session
    ){
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        CustomType customType = new CustomType(400, "请先登录!");
        if (userInfo == null) {
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
        List<ReissueCard> list = reissueCardService.findSelfRC(userInfo.getUserID(),beginDate,endDate);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @RequestMapping(value = "/findByRCId",method = RequestMethod.GET)
    public ResponseEntity<?> findByRCId(@RequestParam("rID") Integer rID,HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        ReissueCard reissueCard = reissueCardService.findByRCId(rID);
        reissueCard.setRchargeName(userInfo.getUsername());
        return new ResponseEntity<>(reissueCard,HttpStatus.OK);
    }

    @RequestMapping(value = "/saveRCard",method = RequestMethod.POST)
    public ResponseEntity<?> saveRCard(@RequestParam("rID") Integer rID,
                                       @RequestParam(value = "rchargeName",required = false) String rchargeName,
                                       @RequestParam(value = "rchargeComment",required = false) String rchargeComment,
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
        if(!smyrights.contains("reissuecard")){
            customType.setMessage("你没有此权限!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        } else {
            ReissueCard reissueCard = reissueCardService.findByRCId(rID);
            int count = 0;
            if (reissueCard.getrStep() == 1) {//部门审核
                reissueCard.setRchargeName(rchargeName);
                reissueCard.setRchargeComment(rchargeComment);
                reissueCard.setRchargeDate(new Date());
                if (rchargeComment.equals("同意")) {
                    reissueCard.setrStep(2);
                } else {
                    reissueCard.setrStep(4);
                }
                count = reissueCardService.updateRCard(reissueCard);
                if (count > 0) {
                    customType.setCode(200);
                    customType.setMessage("保存成功!");
                }
                return new ResponseEntity<>(customType, HttpStatus.OK);
            } else if (reissueCard.getrStep() == 2) {//HR审核
                reissueCard.setRhrName(userInfo.getUsername());
                reissueCard.setRhrComment(rchargeComment);
                reissueCard.setRhrDate(new Date());
                if (rchargeComment.equals("同意")) {
                    reissueCard.setrStep(3);
                } else {
                    reissueCard.setrStep(4);
                }
                count = reissueCardService.updateRCard(reissueCard);
                if (count > 0) {
                    customType.setCode(200);
                    customType.setMessage("保存成功!");
                }
                return new ResponseEntity<>(customType, HttpStatus.OK);
            }
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/findRCards",method = RequestMethod.POST)
    public ResponseEntity<?> findChargeHr(
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(value = "beginDate", required = false) Date beginDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(value = "endDate", required = false) Date endDate, HttpSession session
    ){
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
        if(!smyrights.contains("reissuecard")){
            customType.setMessage("你没有此权限!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        } else {
            List<ReissueCard> list = null;
            if (userInfo.getIsmanager().equals("y") && !userInfo.getDept().equals("HR")) {
                //本部门所有申请
                list = reissueCardService.findChargeRC(userInfo.getDept(), beginDate, endDate);
            } else if (userInfo.getIsmanager().equals("y") && userInfo.getDept().equals("HR")) {//需处理HR部门非经理的申请
                //所有部门申请和hr审批
                list = reissueCardService.findHrRC(beginDate, endDate);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/newRCard",method = RequestMethod.POST)
    public ResponseEntity<?> addRCard(
            @RequestParam("rDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd")Date rDate,
            @RequestParam("rType") String rType,
            @RequestParam("reason") String reason,HttpSession session){
        CustomType customType = new CustomType(400, "保存失败!");
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (userInfo == null) {
            customType.setMessage("请先登录!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
        ReissueCard reissueCard = new ReissueCard();
        reissueCard.setrUserID(userInfo.getUserID());
        reissueCard.setrUserName(userInfo.getUsername());
        reissueCard.setrDept(userInfo.getDept());
        reissueCard.setrDate(rDate);
        reissueCard.setrType(rType);
        reissueCard.setrStep(1);
        reissueCard.setReason(reason);
        int count = reissueCardService.addRCard(reissueCard);
        if (count > 0) {
            customType.setCode(200);
            customType.setMessage("保存成功!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
        return new ResponseEntity<>(customType, HttpStatus.OK);
    }
}

package com.attendance.controller;

import com.attendance.pojo.Travel;
import com.attendance.pojo.UserInfo;
import com.attendance.service.TravelService;
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
public class TravelController {
    @Autowired
    private TravelService travelService;

    //查询自己的出差申请
    @RequestMapping(value = "/findSelfTravel", method = RequestMethod.POST)
    public ResponseEntity<?> findSelfTravel(
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(value = "beginDate", required = false) Date beginDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(value = "endDate", required = false) Date endDate, HttpSession session
    ) {
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        CustomType customType = new CustomType(400, "请先登录!");
        if (userInfo == null) {
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
        List<Travel> list = travelService.findSelfTravels(userInfo.getUserID(), beginDate, endDate);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //根据ID获取出差申请
    @RequestMapping(value = "/findByTravelID",method = RequestMethod.GET)
    public ResponseEntity<?> findByTID(@RequestParam("travelID") Integer travelID,HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        Travel travel = travelService.findById(travelID);
        travel.setTchargeName(userInfo.getUsername());
        return new ResponseEntity<>(travel,HttpStatus.OK);
    }

    //更新出差信息
    @RequestMapping(value = "/saveTravel",method = RequestMethod.POST)
    public ResponseEntity<?> saveTravel(@RequestParam("travelID") Integer travelID,
                                        @RequestParam(value = "tchargeName",required = false) String tchargeName,
                                        @RequestParam(value = "tchargeComment",required = false) String tchargeComment,
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
        if(!smyrights.contains("travel")){
            customType.setMessage("你没有此权限!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        } else {
            Travel travel = travelService.findById(travelID);
            int count = 0;
            if (travel.getTstep() == 1) {//部门审核
                travel.setTchargeName(tchargeName);
                travel.setTchargeComment(tchargeComment);
                travel.setTchargeDate(new Date());
                if (tchargeComment.equals("同意")) {
                    travel.setTstep(2);
                } else {
                    travel.setTstep(4);
                }
                count = travelService.updateTravel(travel);
                if (count > 0) {
                    customType.setCode(200);
                    customType.setMessage("保存成功!");
                }
                return new ResponseEntity<>(customType, HttpStatus.OK);
            } else if (travel.getTstep() == 2) {//HR审核
                travel.setThrName(userInfo.getUsername());
                travel.setThrComment(tchargeComment);
                travel.setThrDate(new Date());
                if (tchargeComment.equals("同意")) {
                    travel.setTstep(3);
                } else {
                    travel.setTstep(4);
                }
                count = travelService.updateTravel(travel);
                if (count > 0) {
                    customType.setCode(200);
                    customType.setMessage("保存成功!");
                }
                return new ResponseEntity<>(customType, HttpStatus.OK);
            }
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
    }

    //查询审核出差申请
    @RequestMapping(value = "/findCHTravel",method = RequestMethod.POST)
    public ResponseEntity<?> findCHTravel(
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
        if(!smyrights.contains("travel")){
            customType.setMessage("你没有此权限!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        } else {
            List<Travel> travels = null;
            if (userInfo.getIsmanager().equals("y") && !userInfo.getDept().equals("HR")) {
                //本部门所有申请
                travels = travelService.findChargetravel(userInfo.getDept(), beginDate, endDate);
            } else if (userInfo.getIsmanager().equals("y") && userInfo.getDept().equals("HR")) {//需处理HR部门非经理的申请
                //所有部门申请和hr审批
                travels = travelService.findHrtravel(beginDate, endDate);
            }
            return new ResponseEntity<>(travels, HttpStatus.OK);
        }
    }

    //新增出差申请
    @RequestMapping(value = "/newTravel", method = RequestMethod.POST)
    public ResponseEntity<?> newTravel(
            @RequestParam("travelDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date travelDate,
            @RequestParam("travelDays") Double travelDays,
            @RequestParam("address") String address,
            @RequestParam("cost") Double cost,
            @RequestParam("remark") String remark, HttpSession session
    ) {
        CustomType customType = new CustomType(400, "保存失败!");
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (userInfo == null) {
            customType.setMessage("请先登录!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
        Travel travel = new Travel();
        travel.setTravelUserID(userInfo.getUserID());
        travel.setTravelUserName(userInfo.getUsername());
        travel.setTravelDate(travelDate);
        travel.setTravelDays(travelDays);
        travel.setAddress(address);
        travel.setCost(cost);
        travel.setTstep(1);
        travel.setRemark(remark);
        travel.setTdept(userInfo.getDept());
        int count = travelService.addTravel(travel);
        if (count > 0) {
            customType.setCode(200);
            customType.setMessage("保存成功!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
        return new ResponseEntity<>(customType, HttpStatus.OK);
    }
}

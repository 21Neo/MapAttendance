package com.attendance.controller;

import com.attendance.pojo.Permission;
import com.attendance.pojo.UserInfo;
import com.attendance.service.PermissionService;
import com.attendance.service.UserInfoService;
import com.attendance.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 获得session
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/getSession", method = RequestMethod.GET)
    public ResponseEntity getSession(HttpSession session) {
        return new ResponseEntity(
                session.getAttribute("userInfo"), HttpStatus.OK);
    }

    /**
     * 注销
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity logout(HttpSession session) {
        session.removeAttribute("userInfo");
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 登录
     *
     * @param loginID
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "/btnLogin", method = RequestMethod.POST)
    public ResponseEntity login(
            @RequestParam(value = "loginID",required = false) String loginID,
            @RequestParam(value = "password",required = false) String password,
            HttpSession session
    ) {
        System.out.println("loginID = "+loginID+" password = "+password);
        CustomType customType = new CustomType(400, "错误的用户名或密码");
        UserInfo userInfo = userInfoService.login(loginID, MD5.getMD5(password));
        System.out.println("用户名 = "+userInfo);
        //System.out.println(permission.getRights() + "======");
        if (userInfo != null) {
            customType.setCode(200);
            customType.setMessage("登录成功");
            session.setAttribute("userInfo", userInfo);
            Permission permission = permissionService.find(userInfo.getRights());
            session.setAttribute("rights", permission.getRights());
        }
        return new ResponseEntity<>(customType, HttpStatus.OK);
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public ResponseEntity changeP(
            /* @RequestParam("username") String username,*/
            @RequestParam("password") String password,
            HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        CustomType customType =
                new CustomType(400, "密码修改失败!");
        if (userInfo != null) {
            UserInfo userInfo1 = new UserInfo();
            userInfo1.setUsername(userInfo.getUsername());
            userInfo1.setPassword(MD5.getMD5(password));
            userInfo1.setUserID(userInfo.getUserID());
            userInfo1.setSchedulingID(userInfo.getSchedulingID());
            userInfo1.setSalary(userInfo.getSalary());
            userInfo1.setIsmanager(userInfo.getIsmanager());
            userInfo1.setRegdate(userInfo.getRegdate());
            userInfo1.setLoginID(userInfo.getLoginID());
            userInfo1.setRights(userInfo.getRights());
            userInfo1.setDept(userInfo.getDept());
            int count = userInfoService.modify(userInfo1);
            if (count > 0) {
                customType.setCode(200);
                customType.setMessage("修改成功!");
            }
        }
        return new ResponseEntity<>(customType, HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<?> find(
            @RequestParam("beginDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date beginDate,
            @RequestParam("endDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,HttpSession session) {
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
        if(!smyrights.contains("user")){
            customType.setMessage("你没有此权限!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        } else {
            List<UserInfo> users = userInfoService.find(beginDate, endDate);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public ResponseEntity remove(
            @RequestParam("userID") Integer userID) {
        CustomType customType =
                new CustomType(400, "删除失败!");
        int count = userInfoService.remove(userID);
        if (count > 0) {
            customType.setCode(200);
            customType.setMessage("删除成功！");
        }
        return new ResponseEntity<>(customType, HttpStatus.OK);
    }

    @RequestMapping(value = "/findByLoginID", method = RequestMethod.GET)
    public ResponseEntity<?> findByLoginID(@RequestParam("loginID") String loginID) {
        CustomType customType =
                new CustomType(400, "该账号已被注册，请从新输入!");
        UserInfo userInfo = userInfoService.findByLoginID(loginID);
        if (userInfo == null/*||userInfo.equals("")*/) {
            customType.setCode(200);
            customType.setMessage("");
        }
        return new ResponseEntity<>(customType, HttpStatus.OK);
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public ResponseEntity<?> findById(@RequestParam("userID") Integer userID) {

        UserInfo userInfo = userInfoService.find(userID);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @RequestMapping(value = "/modifyUser", method = RequestMethod.POST)
    public ResponseEntity modifyUser(
            @RequestParam("password") String password,
            @RequestParam("userID") Integer userID,
            @RequestParam("loginID") String loginID,
            @RequestParam("username") String username,
            @RequestParam("Regdate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date Regdate,
            @RequestParam("rights") String rights,
            @RequestParam("ismanager") String ismanager,
            @RequestParam("dept") String dept,
            @RequestParam("salary") Double salary,
            @RequestParam("schedulingID") Integer schedulingID) {
        CustomType customType =
                new CustomType(400, "修改失败!");
        System.out.println(password + schedulingID + Regdate + password + userID);
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setUsername(username);
        userInfo1.setPassword(password);
        userInfo1.setUserID(userID);
        userInfo1.setSchedulingID(schedulingID);
        userInfo1.setSalary(salary);
        userInfo1.setIsmanager(ismanager);
        userInfo1.setRegdate(Regdate);
        userInfo1.setLoginID(loginID);
        userInfo1.setRights(rights);
        userInfo1.setDept(dept);
        int count = userInfoService.modify(userInfo1);
        if (count > 0) {
            customType.setCode(200);
            customType.setMessage("修改成功!");
        }
        return new ResponseEntity<>(customType, HttpStatus.OK);
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public ResponseEntity addUser(
            @RequestParam("password") String password,
            /*  @RequestParam("userID") Integer userID,*/
            @RequestParam("loginID") String loginID,
            @RequestParam("username") String username,
            @RequestParam("Regdate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date Regdate,
            @RequestParam("rights") String rights,
            @RequestParam("ismanager") String ismanager,
            @RequestParam("dept") String dept,
            @RequestParam("salary") Double salary,
            @RequestParam("schedulingID") Integer schedulingID,HttpSession session) {
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
        if(!smyrights.contains("user")){
            customType.setMessage("你没有此权限!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        } else {
            UserInfo userInfo1 = new UserInfo();
            userInfo1.setUsername(username);
            userInfo1.setPassword(MD5.getMD5(password));
            /*     userInfo1.setUserID(userID);*/
            userInfo1.setSchedulingID(schedulingID);
            userInfo1.setSalary(salary);
            userInfo1.setIsmanager(ismanager);
            userInfo1.setRegdate(Regdate);
            userInfo1.setLoginID(loginID);
            userInfo1.setRights(rights);
            userInfo1.setDept(dept);
            int count = userInfoService.add(userInfo1);
            if (count > 0) {
                customType.setCode(200);
                customType.setMessage("增加成功!");
            }
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
    }

    // 判断验证码
    @RequestMapping(value = "/confirmPic", method = RequestMethod.GET)
    public ResponseEntity confirm(
            HttpServletRequest request,
            @RequestParam("yzm") String yzm
    ) {
        CustomType customType =
                new CustomType(400, "验证码不匹配!");
        HttpSession session = request.getSession();
        String pic = session.getAttribute("Rand").toString();
//        System.out.println("pic = "+pic);
        if (pic.equals(yzm)) {
            customType.setCode(200);
            customType.setMessage("验证成功");
        }
        return new ResponseEntity<>(customType, HttpStatus.OK);
    }
}

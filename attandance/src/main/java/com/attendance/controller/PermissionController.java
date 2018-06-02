package com.attendance.controller;

import com.attendance.pojo.Permission;
import com.attendance.service.PermissionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/rights", method = RequestMethod.GET)
    public ResponseEntity<?> find(HttpSession session) {
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
        if(!smyrights.contains("permission")){
            customType.setMessage("你没有此权限!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        } else {
            List<Permission> list = permissionService.find();
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/userrights", method = RequestMethod.GET)
    public ResponseEntity<?> findur(HttpSession session) {
        CustomType customType = new CustomType(400,"你没有此权限!");
        String myrights = (String) session.getAttribute("rights");
        if(myrights == null || !myrights.contains("user")){
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
        List<Permission> list = permissionService.find();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/findRight",method = RequestMethod.GET)
    public ResponseEntity<?> findById(@RequestParam("pid")Integer pid){
        Permission permission = permissionService.find(pid);
        return new ResponseEntity<>(permission,HttpStatus.OK);

    }

    @RequestMapping(value = "/removeRights",method = RequestMethod.DELETE)
    public ResponseEntity<?> removeRights(@RequestParam("pid") Integer pid){
        CustomType customType = new CustomType(400,"删除失败!");
        int count = permissionService.removeRights(pid);
        if (count>0){
            customType.setCode(200);
            customType.setMessage("删除成功!");
        }
        return new ResponseEntity<>(customType,HttpStatus.OK);
    }

    @RequestMapping(value = "/saveRights",method = RequestMethod.POST)
    public ResponseEntity<?> saveRights(@RequestParam("pid") Integer pid,
                                        @RequestParam("pgroup") String pgroup,
                                        @RequestParam("rights") String rights,HttpSession session){
        Permission permission = null;
        CustomType customType = new CustomType(400,"保存失败!");
        String myrights = (String) session.getAttribute("rights");
        if(myrights == null){
            customType.setMessage("你没有此权限!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        }
        Set<String> smyrights = new HashSet<>();
        String[] mmyrights = myrights.split(",");
        for (String s : mmyrights) {
            smyrights.add(s);//权限分别放入set
        }
        if(!smyrights.contains("permission")){
            customType.setMessage("你没有此权限!");
            return new ResponseEntity<>(customType, HttpStatus.OK);
        } else {

            if (rights.equals("") || rights == null) {
                return new ResponseEntity<>(customType, HttpStatus.OK);
            }
            int count = 0;
            if (pid == 0) {//新增
                Permission p = permissionService.find(pgroup);
                if(p!=null){
                    customType.setMessage("已有此权限组");
                    return new ResponseEntity<>(customType, HttpStatus.OK);
                }
                permission = new Permission();
                permission.setPgroup(pgroup);
                permission.setRights(rights);
                count = permissionService.addPermission(permission);
            } else {
                permission = permissionService.find(pid);
                permission.setRights(rights);
                count = permissionService.modifyRights(permission);
            }
            if (count > 0) {
                customType.setCode(200);
                customType.setMessage("保存成功!");
            }
        }
        return new ResponseEntity<>(customType,HttpStatus.OK);
    }
}

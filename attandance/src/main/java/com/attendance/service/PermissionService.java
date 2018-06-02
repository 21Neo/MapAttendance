package com.attendance.service;

import com.attendance.dao.PermissionMapper;
import com.attendance.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true,propagation = Propagation.NOT_SUPPORTED)
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    public List<Permission> find(){
        return permissionMapper.find();
    }

    public Permission find(Integer pid){
        return permissionMapper.findById(pid);
    }

    public int addPermission(Permission permission){
        return permissionMapper.addPermission(permission);
    }

    public int modifyRights(Permission permission){
        return permissionMapper.modifyPermission(permission);
    }

    public int removeRights(Integer pid){
        return permissionMapper.deleteRights(pid);
    }

    public Permission find(String pgroup){
        return permissionMapper.findByName(pgroup);
    }
}

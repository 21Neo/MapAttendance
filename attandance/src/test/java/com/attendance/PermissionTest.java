package com.attendance;

import com.attendance.pojo.Permission;
import com.attendance.service.PermissionService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class PermissionTest {
    private PermissionService permissionService;
    @Test
    public void find() {
        for(Permission permission : permissionService.find()){
            System.out.println(permission.getRights());
        }

    }
    @Test
    public void findByName(){
        Permission permission = permissionService.find("admin");
        System.out.println(permission.getRights());
    }
    @Before
    public void init(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        permissionService = ctx.getBean("permissionService",PermissionService.class);
    }
}

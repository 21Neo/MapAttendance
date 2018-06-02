package com.attendance.service;

import com.attendance.dao.PermissionMapper;
import com.attendance.dao.SchedulingMapper;
import com.attendance.pojo.Permission;
import com.attendance.pojo.Scheduling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true,propagation = Propagation.NOT_SUPPORTED)
public class SchedulingService {

    @Autowired
    private SchedulingMapper schedulingMapper;

    // 查询班次表
    public List<Scheduling> findSch(){
        return schedulingMapper.findSch();
    }

    // 根据时间段查询班次
    public Scheduling find(String sType){
        return schedulingMapper.findType(sType);
    }

    // 修改班次表
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int modify(Scheduling scheduling){
        return schedulingMapper.modifySch(scheduling);
    }
}

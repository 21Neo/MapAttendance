package com.attendance.service;

import com.attendance.dao.OverTimeMapper;
import com.attendance.pojo.OverTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true,propagation = Propagation.NOT_SUPPORTED)
public class OverTimeService {
    @Autowired
    private OverTimeMapper overTimeMapper;

    public int addOverT(OverTime overTime){
        return overTimeMapper.addOverT(overTime);
    }

    //查询自己的加班申请
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<OverTime> findSelf(Integer userID, Date beginDate, Date endDate){
        return overTimeMapper.findSelfOvt(userID,beginDate,endDate);
    }
    //根据ID查询加班申请
    public OverTime findOvtById(Integer oid){
        return overTimeMapper.findById(oid);
    }

    //更新加班信息
    public int updateOvt(OverTime overTime){
        return overTimeMapper.updateOvt(overTime);
    }

    //查询本部门的加班申请
    public List<OverTime > findChargeovt(String odept,Date beginDate,Date endDate){
        return overTimeMapper.findChargeovt(odept,beginDate,endDate);
    }
    //查询所有部门步骤为2（部门审批通过）的请假申请
    public List<OverTime> findHrovt(Date beginDate,Date endDate){
        return overTimeMapper.findHrovt(beginDate,endDate);
    }

}

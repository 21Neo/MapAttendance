package com.attendance.service;

import com.attendance.dao.TravelMapper;
import com.attendance.pojo.Travel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true,propagation = Propagation.NOT_SUPPORTED)
public class TravelService {
    @Autowired
    private TravelMapper travelMapper;

    //查询自己的出差申请
    public List<Travel> findSelfTravels(Integer travelUserID, Date beginDate,Date endDate){
        return travelMapper.findSelfTravel(travelUserID,beginDate,endDate);
    }

    //新增出差申请
    public int addTravel(Travel travel){
        return travelMapper.addTravel(travel);
    }

    //部门审核出差申请
    public List<Travel> findChargetravel(String tdept,Date beginDate,Date endDate){
        return travelMapper.findChargetravel(tdept, beginDate, endDate);
    }

    //查询所有部门步骤为2（部门审批通过）的出差申请
    public List<Travel> findHrtravel(Date beginDate,Date endDate){
        return travelMapper.findHrtravel(beginDate, endDate);
    }

    //根据ID查询
    public Travel findById(Integer travelID){
        return travelMapper.findTravelById(travelID);
    }

    //更新出差申请
    public int updateTravel(Travel travel){
        return travelMapper.updateTravel(travel);
    }
}

package com.attendance.service;

import com.attendance.dao.AskForLeaveMapper;
import com.attendance.pojo.AskForLeave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true,propagation = Propagation.NOT_SUPPORTED)
public class AskForLeaveService {
    @Autowired
    private AskForLeaveMapper askForLeaveMapper;

    //添加请假申请
    public int addAfl(AskForLeave askForLeave){
        return askForLeaveMapper.addAfl(askForLeave);
    }

    //查询自己的请假申请
    public List<AskForLeave> findSelf(Integer userid, Date beginDate,Date endDate){
        return askForLeaveMapper.findSelfafl(userid,beginDate,endDate);
    }

    //根据ID查询请假申请
    public AskForLeave findAflById(Integer askid){
        return askForLeaveMapper.findById(askid);
    }

    //更新请假信息
    public int updateAfl(AskForLeave askForLeave){
        return askForLeaveMapper.updateAfl(askForLeave);
    }

    //查询本部门的请假申请
    public List<AskForLeave> findChargeafl(String askDept,Date beginDate,Date endDate){
        return askForLeaveMapper.findChargeafl(askDept,beginDate,endDate);
    }

    //查询所有部门步骤为2（部门审批通过）的请假申请
    public List<AskForLeave> findHrafl(Date beginDate,Date endDate){
        return askForLeaveMapper.findHrafl(beginDate,endDate);
    }
}

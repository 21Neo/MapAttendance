package com.attendance.service;

import com.attendance.dao.ReissueCardMapper;
import com.attendance.pojo.ReissueCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true,propagation = Propagation.NOT_SUPPORTED)
public class ReissueCardService {
    @Autowired
    private ReissueCardMapper reissueCardMapper;

    //新增补卡申请
    public int addRCard(ReissueCard reissueCard){
        return reissueCardMapper.addRCard(reissueCard);
    }

    //查询自己的补卡申请
    public List<ReissueCard> findSelfRC(Integer rUserID, Date beginDate,Date endDate){
        return reissueCardMapper.findSelfRC(rUserID, beginDate, endDate);
    }

    //本部门补卡申请
    public List<ReissueCard> findChargeRC(String rDept, Date beginDate,Date endDate){
        return reissueCardMapper.findChargeRC(rDept, beginDate, endDate);
    }

    //HR审核补卡申请
    public List<ReissueCard> findHrRC(Date beginDate,Date endDate){
        return reissueCardMapper.findHrRC(beginDate, endDate);
    }

    //根据ID查补卡申请
    public ReissueCard findByRCId(Integer rID){
        return reissueCardMapper.findByRCId(rID);
    }

    //更新补卡信息
    public int updateRCard(ReissueCard reissueCard){
        return reissueCardMapper.updateRCard(reissueCard);
    }
}

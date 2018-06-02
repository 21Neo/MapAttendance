package com.attendance.dao;

import com.attendance.pojo.ReissueCard;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReissueCardMapper {
    //新增补卡申请
    @Insert("insert into reissuecard(rUserID,rUserName,rDept,rDate,rType,rStep,reason) " +
            "values(#{rUserID},#{rUserName},#{rDept},#{rDate},#{rType},#{rStep},#{reason})")
    int addRCard(ReissueCard reissueCard);

    //查找自己的补卡申请
    List<ReissueCard> findSelfRC(@Param("rUserID") Integer rUserID,
                                 @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    //查询本部门步骤为1（提出申请）的请假申请
    List<ReissueCard> findChargeRC(@Param("rDept") String rDept,@Param("beginDate") Date beginDate,
                                   @Param("endDate") Date endDate);

    //查询所有部门步骤为2（部门申请通过）的请假申请
    List<ReissueCard> findHrRC(@Param("beginDate") Date beginDate,
                               @Param("endDate") Date endDate);

    //根据ID查找补卡信息
    @Select("SELECT rID,rUserID,rUserName,rDept,rDate,rType,rchargeName,rchargeComment,rchargeDate,rStep,reason from reissuecard where rID=#{rID}")
    ReissueCard findByRCId(Integer rID);

    //更新请假信息
    @Update("update reissuecard set rchargeName=#{rchargeName},rchargeComment=#{rchargeComment},rchargeDate=#{rchargeDate},rhrName=#{rhrName},rhrComment=#{rhrComment},rhrDate=#{rhrDate},rStep=#{rStep} where rID=#{rID}")
    int updateRCard(ReissueCard reissueCard);
}

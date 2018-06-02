package com.attendance.dao;

import com.attendance.pojo.AskForLeave;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AskForLeaveMapper {

    //查询自己的请假申请
    List<AskForLeave> findSelfafl(@Param("userid") Integer userid, @Param("beginDate") Date beginDate,
                                  @Param("endDate") Date endDate);

    //查询本部门步骤为1（提出申请）的请假申请
    List<AskForLeave> findChargeafl(@Param("askDept") String askDept,@Param("beginDate") Date beginDate,
                                    @Param("endDate") Date endDate);

    //查询所有部门步骤为2（部门申请通过）的请假申请
    List<AskForLeave> findHrafl(@Param("beginDate") Date beginDate,
                                @Param("endDate") Date endDate);

    //根据ID查找请假信息
    @Select("select askID,userID,userName,entryDate,days,chargeName,chargeComment,chargeDate,askDept,step,remark from askforleave where askID=#{askid}")
    AskForLeave findById(Integer askid);

    //更新请假信息
    @Update("update askforleave set chargeName=#{chargeName},chargeComment=#{chargeComment},chargeDate=#{chargeDate},hrName=#{hrName},hrComment=#{hrComment},hrDate=#{hrDate},step=#{step} where askID=#{askid}")
    int updateAfl(AskForLeave askForLeave);

    //新增请假申请
    @Insert("insert into askforleave(userID, userName, entryDate, days, askDept, step, remark) " +
            "values(#{userid},#{userName},#{entryDate},#{days},#{askDept},#{step},#{remark})")
    int addAfl(AskForLeave askForLeave);
}

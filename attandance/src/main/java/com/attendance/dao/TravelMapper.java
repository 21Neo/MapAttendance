package com.attendance.dao;

import com.attendance.pojo.Travel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TravelMapper {
    //查询自己的出差申请
    List<Travel> findSelfTravel(@Param("travelUserID") Integer travelUserID,@Param("beginDate") Date beginDate,
                                @Param("endDate") Date endDate);

    //新增出差申请
    @Insert("insert into travel(travelUserID,travelUserName,travelDate,travelDays,address,cost,tstep,remark,tdept) values(#{travelUserID},#{travelUserName},#{travelDate},#{travelDays},#{address},#{cost},#{tstep},#{remark},#{tdept})")
    int addTravel(Travel travel);

    //查询本部门步骤为1（提出申请）的出差申请
    List<Travel> findChargetravel(@Param("tdept") String tdept,@Param("beginDate") Date beginDate,
                                  @Param("endDate") Date endDate);

    //查询所有部门步骤为2（部门申请通过）的请假申请
    List<Travel> findHrtravel(@Param("beginDate") Date beginDate,
                              @Param("endDate") Date endDate);

    //根据ID查出差信息
    @Select("SELECT travelID,travelUserID,travelUserName,travelDate,travelDays,address,cost,tchargeName,tchargeComment,tchargeDate,tstep,remark,tdept from travel where travelID=#{travelID}")
    Travel findTravelById(Integer travelID);

    //更新出差申请
    @Update("update travel set tchargeName=#{tchargeName},tchargeComment=#{tchargeComment},tchargeDate=#{tchargeDate},thrName=#{thrName},thrComment=#{thrComment},thrDate=#{thrDate},tstep=#{tstep} where travelID=#{travelID}")
    int updateTravel(Travel travel);
}

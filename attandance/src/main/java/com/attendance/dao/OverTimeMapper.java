package com.attendance.dao;
import com.attendance.pojo.OverTime;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OverTimeMapper {
    //新增加班申请
    @Insert("insert into overtime(oUserID, oUserName, overTimeDate, hours,odept,ostep, reason) " +
            "values(#{oUserID},#{oUserName},#{overTimeDate},#{hours},#{odept},#{ostep},#{reason})")
    int addOverT(OverTime overTime);
    //查询自己的加班申请
    List<OverTime> findSelfOvt(@Param("userID") Integer userID, @Param("beginDate") Date beginDate,
                               @Param("endDate") Date endDate);
    //根据ID查找加班信息
    @Select("select oid,oUserID,oUserName,overTimeDate,hours,ochargeName,ochargeComment,ochargeDate,odept,ostep,reason from overtime where oid=#{oid}")
    OverTime findById(Integer oid);

    //更新加班信息
    @Update("update overtime set ochargeName=#{ochargeName},ochargeComment=#{ochargeComment},ochargeDate=#{ochargeDate},ohrName=#{ohrName},ohrComment=#{ohrComment},ohrDate=#{ohrDate},ostep=#{ostep} where oid=#{oid}")
    int updateOvt(OverTime overTime);

    //查询本部门步骤为1（提出申请）的加班申请
    List<OverTime> findChargeovt(@Param("odept") String odept,
                                 @Param("beginDate") Date beginDate,
                                 @Param("endDate") Date endDate);

    //查询所有部门步骤为2（部门申请通过）的加班申请
    List<OverTime> findHrovt(@Param("beginDate") Date beginDate,
                             @Param("endDate") Date endDate);
}

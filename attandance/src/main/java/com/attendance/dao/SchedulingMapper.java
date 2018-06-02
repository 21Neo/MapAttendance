package com.attendance.dao;

import com.attendance.pojo.Scheduling;
import com.attendance.pojo.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 班次映射器接口
 */
@Repository
public interface SchedulingMapper {
    // 查询班次
    @Select("Select sID,sType,date_format(sStartTime,'%T') sStartTime,date_format(sEndTime,'%T') sEndTime from scheduling ")
    List<Scheduling> findSch();

    // 根据班次类型查询
    @Select("select sID,sType,date_format(sStartTime,'%T') sStartTime,date_format(sEndTime,'%T') sEndTime from scheduling where sType=#{sType}")
    Scheduling findType(String sType);

    // 修改班次时间
    @Update("update scheduling set sStartTime = #{sStartTime},sEndTime = #{sEndTime} where sID=#{sID}")
    int modifySch(Scheduling scheduling);
}

package com.attendance.dao;

import com.attendance.pojo.Permission;
import com.attendance.pojo.UserAttendance;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AttendanceMapper {
    // 查询打卡记录
    @Select("select aID,aUserID,auserName,beginTime,amType,endTime,pmType,distance,aDept,attRemark from userattendance where aUserID=#{aUserID}")
    List<UserAttendance> findById(Integer aUserID);

    // 查询当天的打卡记录
    @Select("select aID,aUserID,auserName,beginTime,amType,endTime,pmType,distance,aDept,attRemark from userattendance where aUserID=#{aUserID} and to_days(beginTime)=to_days(#{nowTime})")
    UserAttendance findNow(@Param("aUserID") Integer aUserID, @Param("nowTime") Date nowTime);

    // 修改打卡记录
    @Update("update userattendance set attRemark=#{attRemark} where aUserID=#{aUserID} and to_days(beginTime)=to_days(#{nowTime})")
    int modify(@Param("aUserID") Integer aUserID, @Param("attRemark") String attRemark, @Param("nowTime") Date nowTime);

    // 模糊查询
    List<UserAttendance> findByParam(@Param("aUserID") Integer aUserID, @Param("beforeTime") Date beforeTime, @Param("afterTime") Date afterTime);

    // 查询所有打卡记录
//    @Select("select aID,aUserID,auserName,beginTime,amType,endTime,pmType,distance,aDept,attRemark from userattendance")
    List<UserAttendance> findAll(
            @Param("aDept") String aDept,
            @Param("beginTime") Date beginTime);

    // 查询是否打上班卡
    @Select("select aID,aUserID,auserName,beginTime,endTime,distance,aDept from userattendance where date_format(beginTime,'%b-%d-%Y') = date_format(#{nowTime},'%b-%d-%Y') and auserName=#{auserName}")
    UserAttendance findByTime(@Param("nowTime") Date nowTime, @Param("auserName") String auserName);

    // 上班打卡
    @Insert("insert into userattendance(aUserID,auserName,beginTime,amType,endTime,pmType,distance,attRemark,aDept) values(#{aUserID},#{auserName},#{beginTime},#{amType},#{endTime},#{pmType},#{distance},#{attRemark},#{aDept})")
    int addAttenDance(UserAttendance userAttendance);

    // 下班打卡
    @Update("update userattendance set endTime=#{endTime},pmType=#{pmType},attRemark=#{attRemark} where aID=#{aID}")
    int modifyAttendance(UserAttendance userAttendance);

}

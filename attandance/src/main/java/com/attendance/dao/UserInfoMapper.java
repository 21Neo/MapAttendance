package com.attendance.dao;
import com.attendance.pojo.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
/**
 * 员工映射器接口
 */
@Repository
public interface UserInfoMapper {
    @Select("select userID,loginID,username,password,Regdate,rights,ismanager,dept,salary,schedulingID from userinfo where loginID=#{loginID} and password=#{password}")
    UserInfo login(@Param("loginID") String loginID,
                   @Param("password") String password);

    @Select("select userID,loginID,username,password,Regdate,rights,ismanager,dept,salary,schedulingID from userinfo")
    List<UserInfo> find();
/*    @Select("select userID,loginID,username,password,Regdate,rights,ismanager,dept,salary,schedulingID from userinfo where Regdate between #{beginDate} and #{endDate}")*/
    List<UserInfo> findByDate(@Param("beginDate") Date beginDate,
                              @Param("endDate") Date endDate);

    @Select("select userID,loginID,username,password,Regdate,rights,ismanager,dept,salary,schedulingID from userinfo where userID=#{userID}")
    UserInfo findByID(@Param("userID") Integer userID);

    @Select("select userID,loginID,username,password,Regdate,rights,ismanager,dept,salary,schedulingID from userinfo where loginID=#{loginID}")
    UserInfo findByLoginID(@Param("loginID") String loginID);
    @Insert("insert into userinfo(loginID,username,password,Regdate,rights,ismanager,dept,salary,schedulingID)values(#{loginID},#{username},#{password},#{Regdate},#{rights},#{ismanager},#{dept},#{salary},#{schedulingID})")
    int add(UserInfo userInfo);

    @Delete("delete from userinfo where userID=#{userID}")
    int remove(Integer id);
    @Update("update userinfo set loginID=#{loginID},username=#{username},password=#{password},Regdate=#{Regdate},rights=#{rights},ismanager=#{ismanager},dept=#{dept},salary=#{salary},schedulingID=#{schedulingID} where userID=#{userID}")
    int modify(UserInfo userInfo);

}

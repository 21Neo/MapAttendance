package com.attendance.dao;

import com.attendance.pojo.Permission;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper {

    @Select("select pid,pgroup,rights from permission where pid=#{pid}")
    Permission findById(Integer pid);

    @Select("select pid,pgroup,rights from permission")
    List<Permission> find();

    @Insert("insert into permission(pgroup,rights) values(#{pgroup},#{rights})")
    int addPermission(Permission permission);

    @Update("update permission set rights=#{rights} where pid=#{pid}")
    int modifyPermission(Permission permission);

    @Delete("delete from permission where pid=#{pid}")
    int deleteRights(Integer pid);

    Permission findByName(@Param("pgroup") String pgroup);
}

package com.attendance.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class UserInfo implements Serializable{
    private static final long serialVersionUID = 4048745831209423991L;
    private Integer userID;
    private  String loginID;
     private  String username;
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date Regdate;
    private String rights;
    private String ismanager;
    private String dept;
    private Double salary;
    private Integer schedulingID;

    public UserInfo() {
    }

    public UserInfo(Integer userID, String loginID, String username, String password, Date regdate, String rights, String ismanager, String dept, Double salary, Integer schedulingID) {
        this.userID = userID;
        this.loginID = loginID;
        this.username = username;
        this.password = password;
        Regdate = regdate;
        this.rights = rights;
        this.ismanager = ismanager;
        this.dept = dept;
        this.salary = salary;
        this.schedulingID = schedulingID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegdate() {
        return Regdate;
    }

    public void setRegdate(Date regdate) {
        Regdate = regdate;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public String getIsmanager() {
        return ismanager;
    }

    public void setIsmanager(String ismanager) {
        this.ismanager = ismanager;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getSchedulingID() {
        return schedulingID;
    }

    public void setSchedulingID(Integer schedulingID) {
        this.schedulingID = schedulingID;
    }
}

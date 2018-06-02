package com.attendance.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;
import java.util.Date;

public class UserAttendance implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer aID;
    private String auserName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
    private String pmType;
    private Double distance;
    private String aDept;
    private String amType;
    private String attRemark;
    private Integer aUserID;

    public UserAttendance() {
    }

    @Override
    public String toString() {
        return "UserAttendance{" +
                "aID=" + aID +
                ", auserName='" + auserName + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", pmType='" + pmType + '\'' +
                ", distance=" + distance +
                ", aDept='" + aDept + '\'' +
                ", amType='" + amType + '\'' +
                ", attRemark='" + attRemark + '\'' +
                ", aUserID=" + aUserID +
                '}';
    }

    public UserAttendance(Integer aID, String auserName, Date beginTime, Date endTime, String pmType, Double distance, String aDept, String amType, String attRemark, Integer aUserID) {
        this.aID = aID;
        this.auserName = auserName;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.pmType = pmType;
        this.distance = distance;
        this.aDept = aDept;
        this.amType = amType;
        this.attRemark = attRemark;
        this.aUserID = aUserID;
    }

    public Integer getaID() {
        return aID;
    }

    public void setaID(Integer aID) {
        this.aID = aID;
    }

    public String getAuserName() {
        return auserName;
    }

    public void setAuserName(String auserName) {
        this.auserName = auserName;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPmType() {
        return pmType;
    }

    public void setPmType(String pmType) {
        this.pmType = pmType;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getaDept() {
        return aDept;
    }

    public void setaDept(String aDept) {
        this.aDept = aDept;
    }

    public String getAmType() {
        return amType;
    }

    public void setAmType(String amType) {
        this.amType = amType;
    }

    public String getAttRemark() {
        return attRemark;
    }

    public void setAttRemark(String attRemark) {
        this.attRemark = attRemark;
    }

    public Integer getaUserID() {
        return aUserID;
    }

    public void setaUserID(Integer aUserID) {
        this.aUserID = aUserID;
    }
}

package com.attendance.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;
public class OverTime implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer oid;
    private Integer oUserID;
    private String oUserName;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date overTimeDate;
    private Double hours;
    private String reason;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date oStartTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date oEndTime;
    private String ochargeName;
    private String ochargeComment;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date ochargeDate;
    private String ohrName;
    private String ohrComment;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date ohrDate;
    private String odept;
    private Integer ostep;

    public OverTime() {
    }
    public OverTime(Integer oid, Integer oUserID, String oUserName, Date overTimeDate, Double hours, String reason, Date oStartTime, Date oEndTime, String ochargeName, String ochargeComment, Date ochargeDate, String ohrName, String ohrComment, Date ohrDate, String odept, Integer ostep) {
        this.oid = oid;
        this.oUserID = oUserID;
        this.oUserName = oUserName;
        this.overTimeDate = overTimeDate;
        this.hours = hours;
        this.reason = reason;
        this.oStartTime = oStartTime;
        this.oEndTime = oEndTime;
        this.ochargeName = ochargeName;
        this.ochargeComment = ochargeComment;
        this.ochargeDate = ochargeDate;
        this.ohrName = ohrName;
        this.ohrComment = ohrComment;
        this.ohrDate = ohrDate;
        this.odept = odept;
        this.ostep = ostep;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getoUserID() {
        return oUserID;
    }

    public void setoUserID(Integer oUserID) {
        this.oUserID = oUserID;
    }

    public String getoUserName() {
        return oUserName;
    }

    public void setoUserName(String oUserName) {
        this.oUserName = oUserName;
    }

    public Date getOverTimeDate() {
        return overTimeDate;
    }

    public void setOverTimeDate(Date overTimeDate) {
        this.overTimeDate = overTimeDate;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getoStartTime() {
        return oStartTime;
    }

    public void setoStartTime(Date oStartTime) {
        this.oStartTime = oStartTime;
    }

    public Date getoEndTime() {
        return oEndTime;
    }

    public void setoEndTime(Date oEndTime) {
        this.oEndTime = oEndTime;
    }

    public String getOchargeName() {
        return ochargeName;
    }

    public void setOchargeName(String ochargeName) {
        this.ochargeName = ochargeName;
    }

    public String getOchargeComment() {
        return ochargeComment;
    }

    public void setOchargeComment(String ochargeComment) {
        this.ochargeComment = ochargeComment;
    }

    public Date getOchargeDate() {
        return ochargeDate;
    }

    public void setOchargeDate(Date ochargeDate) {
        this.ochargeDate = ochargeDate;
    }

    public String getOhrName() {
        return ohrName;
    }

    public void setOhrName(String ohrName) {
        this.ohrName = ohrName;
    }

    public String getOhrComment() {
        return ohrComment;
    }

    public void setOhrComment(String ohrComment) {
        this.ohrComment = ohrComment;
    }

    public Date getOhrDate() {
        return ohrDate;
    }

    public void setOhrDate(Date ohrDate) {
        this.ohrDate = ohrDate;
    }

    public String getOdept() {
        return odept;
    }

    public void setOdept(String odept) {
        this.odept = odept;
    }

    public Integer getOstep() {
        return ostep;
    }

    public void setOstep(Integer ostep) {
        this.ostep = ostep;
    }
}

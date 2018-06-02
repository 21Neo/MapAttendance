package com.attendance.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class ReissueCard implements Serializable {
    private static final long serialVersionUID = -4849462177464676452L;
    private Integer rID;
    private Integer rUserID;
    private String rUserName;
    private String rDept;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date rDate;
    private String rType;
    private String rchargeName;
    private String rchargeComment;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date rchargeDate;
    private String rhrName;
    private String rhrComment;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date rhrDate;
    private Integer rStep;
    private String reason;

    public ReissueCard() {
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getrID() {
        return rID;
    }

    public void setrID(Integer rID) {
        this.rID = rID;
    }

    public Integer getrUserID() {
        return rUserID;
    }

    public void setrUserID(Integer rUserID) {
        this.rUserID = rUserID;
    }

    public String getrUserName() {
        return rUserName;
    }

    public void setrUserName(String rUserName) {
        this.rUserName = rUserName;
    }

    public String getrDept() {
        return rDept;
    }

    public void setrDept(String rDept) {
        this.rDept = rDept;
    }

    public Date getrDate() {
        return rDate;
    }

    public void setrDate(Date rDate) {
        this.rDate = rDate;
    }

    public String getrType() {
        return rType;
    }

    public void setrType(String rType) {
        this.rType = rType;
    }

    public String getRchargeName() {
        return rchargeName;
    }

    public void setRchargeName(String rchargeName) {
        this.rchargeName = rchargeName;
    }

    public String getRchargeComment() {
        return rchargeComment;
    }

    public void setRchargeComment(String rchargeComment) {
        this.rchargeComment = rchargeComment;
    }

    public Date getRchargeDate() {
        return rchargeDate;
    }

    public void setRchargeDate(Date rchargeDate) {
        this.rchargeDate = rchargeDate;
    }

    public String getRhrName() {
        return rhrName;
    }

    public void setRhrName(String rhrName) {
        this.rhrName = rhrName;
    }

    public String getRhrComment() {
        return rhrComment;
    }

    public void setRhrComment(String rhrComment) {
        this.rhrComment = rhrComment;
    }

    public Date getRhrDate() {
        return rhrDate;
    }

    public void setRhrDate(Date rhrDate) {
        this.rhrDate = rhrDate;
    }

    public Integer getrStep() {
        return rStep;
    }

    public void setrStep(Integer rStep) {
        this.rStep = rStep;
    }
}

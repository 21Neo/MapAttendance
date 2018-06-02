package com.attendance.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Travel implements Serializable {
    private static final long serialVersionUID = -3017143727685458706L;
    private int travelID;
    private int travelUserID;
    private String travelUserName;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date travelDate;
    private Double travelDays;
    private String address;
    private Double cost;
    private String tchargeName;
    private String tchargeComment;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date tchargeDate;
    private String thrName;
    private String thrComment;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date thrDate;
    private Integer tstep;
    private String remark;
    private String tdept;

    public Travel() {
    }

    public String getTdept() {
        return tdept;
    }

    public void setTdept(String tdept) {
        this.tdept = tdept;
    }

    public int getTravelID() {
        return travelID;
    }

    public void setTravelID(int travelID) {
        this.travelID = travelID;
    }

    public int getTravelUserID() {
        return travelUserID;
    }

    public void setTravelUserID(int travelUserID) {
        this.travelUserID = travelUserID;
    }

    public String getTravelUserName() {
        return travelUserName;
    }

    public void setTravelUserName(String travelUserName) {
        this.travelUserName = travelUserName;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public Double getTravelDays() {
        return travelDays;
    }

    public void setTravelDays(Double travelDays) {
        this.travelDays = travelDays;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getTchargeName() {
        return tchargeName;
    }

    public void setTchargeName(String tchargeName) {
        this.tchargeName = tchargeName;
    }

    public String getTchargeComment() {
        return tchargeComment;
    }

    public void setTchargeComment(String tchargeComment) {
        this.tchargeComment = tchargeComment;
    }

    public Date getTchargeDate() {
        return tchargeDate;
    }

    public void setTchargeDate(Date tchargeDate) {
        this.tchargeDate = tchargeDate;
    }

    public String getThrName() {
        return thrName;
    }

    public void setThrName(String thrName) {
        this.thrName = thrName;
    }

    public String getThrComment() {
        return thrComment;
    }

    public void setThrComment(String thrComment) {
        this.thrComment = thrComment;
    }

    public Date getThrDate() {
        return thrDate;
    }

    public void setThrDate(Date thrDate) {
        this.thrDate = thrDate;
    }

    public Integer getTstep() {
        return tstep;
    }

    public void setTstep(Integer tstep) {
        this.tstep = tstep;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

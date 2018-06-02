package com.attendance.pojo;

import java.io.Serializable;
import java.util.Date;

public class Scheduling implements Serializable{
    private static final long serialVersionUID = 4048745831209423991L;

    private Integer sID;
    private String sType;
    private String sStartTime;
    private String sEndTime;

    public Scheduling() {
    }

    @Override
    public String toString() {
        return "Scheduling{" +
                "sID=" + sID +
                ", sType='" + sType + '\'' +
                ", sStartTime='" + sStartTime + '\'' +
                ", sEndTime='" + sEndTime + '\'' +
                '}';
    }

    public Integer getsID() {
        return sID;
    }

    public void setsID(Integer sID) {
        this.sID = sID;
    }

    public String getsType() {
        return sType;
    }

    public void setsType(String sType) {
        this.sType = sType;
    }

    public String getsStartTime() {
        return sStartTime;
    }

    public void setsStartTime(String sStartTime) {
        this.sStartTime = sStartTime;
    }

    public String getsEndTime() {
        return sEndTime;
    }

    public void setsEndTime(String sEndTime) {
        this.sEndTime = sEndTime;
    }

    public Scheduling(Integer sID, String sType, String sStartTime, String sEndTime) {
        this.sID = sID;
        this.sType = sType;
        this.sStartTime = sStartTime;
        this.sEndTime = sEndTime;
    }
}

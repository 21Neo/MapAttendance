package com.attendance.pojo;

import javax.sql.rowset.serial.SerialArray;
import java.io.Serializable;
import java.util.Date;

public class Car implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Double price;
    private Date createDate;

    public Car() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Car(Integer id, String name, Double price, Date createDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createDate = createDate;
    }
}

package com.yatang.sc.facade.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tangqi on 2018/1/10 0:51.
 */
public class MongoTestBeanDto implements Serializable {

    private String id;

    private String name;

    private String password;

    private Integer age;

    private Date startDate;

    private Date endDate;

    private long startLong;

    private long endLong;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getStartLong() {
        return startLong;
    }

    public void setStartLong(Long startLong) {
        this.startLong = startLong;
    }

    public Long getEndLong() {
        return endLong;
    }

    public void setEndLong(Long endLong) {
        this.endLong = endLong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "MongoTestBeanDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", startLong=" + startLong +
                ", endLong=" + endLong +
                '}';
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

package com.example.calendertest;

import cn.bmob.v3.BmobObject;

public class Calendar extends BmobObject {
    private String UserName;
    private String GroupName;
    private Integer color;
    private Long timeInMillis;
    private String data;

    public Calendar setUserName(String username){
        this.UserName=username;
        return this;
    }
    public String getUserName(){
        return UserName;
    }
    public Calendar setGroupName(String GroupName){
        this.GroupName=GroupName;
        return this;
    }
    public String getGroupName(){
        return GroupName;
    }
    public Integer getcolor(){
        return color;
    }
    public Calendar setColor(Integer col){
        this.color=col;
        return this;
    }
    public Long getTimeInMillis(){
        return this.timeInMillis;
    }
    public Calendar setTimeInMillis(Long timeInMillis) {
        this.timeInMillis = timeInMillis;
        return this;
    }
    public String getData(){
        return this.data;
    }
    public Calendar setData(String da){
        this.data=da;
        return this;
    }
}

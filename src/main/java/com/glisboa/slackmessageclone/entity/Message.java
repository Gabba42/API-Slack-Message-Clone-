package com.glisboa.slackmessageclone.entity;

import java.util.Date;

public class Message {

    private Integer id;
    private String messageBody;
//    private Date date;
    private Integer userId;

    public Message() {  }

    public Message(Integer id, String messageBody, Integer userId) {
        this.id = id;
        this.messageBody = messageBody;
//        this.date = date;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

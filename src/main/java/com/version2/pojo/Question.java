package com.version2.pojo;

import java.sql.Timestamp;

public class Question {
    private int qID;
    private String question;
    private String account;
    private Timestamp createdTime;

    @Override
    public String toString() {
        return "Question{" +
                "qID=" + qID +
                ", question='" + question + '\'' +
                ", account='" + account + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }

    public int getQID() {
        return qID;
    }

    public String getQuestion() {
        return question;
    }

    public String getAccount() {
        return account;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }
}
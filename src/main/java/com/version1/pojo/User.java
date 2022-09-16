package com.version1.pojo;

public class User {
    private String account;
    private String password;
    private String identity;

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", identity='" + identity + '\'' +
                '}';
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}

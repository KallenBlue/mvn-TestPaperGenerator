package com.version1.pojo;

public class User {
    private String account;
    private String primary;
    private String password;
    private String identity;

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", primary='" + primary + '\'' +
                ", password='" + password + '\'' +
                ", identity='" + identity + '\'' +
                '}';
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}

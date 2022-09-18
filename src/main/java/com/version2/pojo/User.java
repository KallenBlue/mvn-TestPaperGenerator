package com.version2.pojo;

public class User {
    private String account;
    private String password;
    private String identity;

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

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

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getIdentity() {
        return identity;
    }
}

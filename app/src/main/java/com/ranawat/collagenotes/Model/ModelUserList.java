package com.ranawat.collagenotes.Model;

public class ModelUserList {

    String   email, name , lastmsg, userId;

    public ModelUserList() {
    }



    public ModelUserList(String email, String name , String lastmsg,String userId) {
        this.email = email;
        this.name = name;
        this.lastmsg = lastmsg;
        this.userId = userId;
    }

    public String getUserId(String key) {
        return userId;
    }

    public String getUserId() {
        return userId;
    }



    public String getLastmsg() {
        return lastmsg;
    }

    public void setLastmsg(String lastmsg) {
        this.lastmsg = lastmsg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

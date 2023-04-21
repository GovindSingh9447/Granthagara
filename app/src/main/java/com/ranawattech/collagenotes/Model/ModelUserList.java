package com.ranawattech.collagenotes.Model;

public class ModelUserList {

    String   email, name , lastmsg, userId ,  profilePic;
    int unSeenMsg;

    public ModelUserList() {
    }



    public ModelUserList(String email, String name , String lastmsg, String userId, int unSeenMsg, String profilePic) {
        this.email = email;
        this.name = name;
        this.lastmsg = lastmsg;
        this.userId = userId;
        this.profilePic=profilePic;
        this.unSeenMsg=unSeenMsg;
    }

    public String getUserId(String key) {
        return userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUnSeenMsg() {
        return unSeenMsg;
    }

    public void setUnSeenMsg(int unSeenMsg) {
        this.unSeenMsg = unSeenMsg;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
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

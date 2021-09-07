package com.ranawat.collagenotes.Model;

public class ModelCollage {

    //Spelling must be same

    String cid,collage ,uid;
    long timestamp;

    //constructor empty is for firebase
    public ModelCollage() {

    }

    //parametrize constructor


    public ModelCollage(String cid, String collage, String uid, long timestamp) {
        this.cid = cid;
        this.collage = collage;
        this.uid = uid;
        this.timestamp = timestamp;
    }


    /*-------------------getter and setter---------------------*/

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCollage() {
        return collage;
    }

    public void setCollage(String collage) {
        this.collage = collage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

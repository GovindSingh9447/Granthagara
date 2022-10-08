package com.ranawat.collagenotes.Model;

public class ModelCollage {

    //Spelling must be same

    String cid,collage ,uid;
    long timestamp;

    String img;

    //constructor empty is for firebase
    public ModelCollage() {

    }

    //parametrize constructor


    public ModelCollage(String cid, String collage , String img, String uid, long timestamp) {
        this.cid = cid;
        this.collage = collage;
        this.uid = uid;
        this.img = img;
        this.timestamp = timestamp;
    }


    /*-------------------getter and setter---------------------*/


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

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

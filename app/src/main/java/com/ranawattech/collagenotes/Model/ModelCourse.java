package com.ranawattech.collagenotes.Model;

public class ModelCourse {

    //variable
    String cid, course, collage, collageName;
    long timestamp;

    //constructor

    public ModelCourse() {
    }

    public ModelCourse(String cid, String course, String collage, String collageName, long timestamp) {
        this.cid = cid;
        this.course = course;
        this.collage = collage;
        this.collageName = collageName;
        this.timestamp = timestamp;
    }

    /*---------Getter/Setter-------------------*/

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCollage() {
        return collage;
    }

    public void setCollage(String collage) {
        this.collage = collage;
    }

    public String getCollageName() {
        return collageName;
    }

    public void setCollageName(String collageName) {
        this.collageName = collageName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

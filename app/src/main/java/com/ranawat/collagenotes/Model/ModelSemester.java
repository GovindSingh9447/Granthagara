package com.ranawat.collagenotes.Model;

public class ModelSemester {

    //variable
    String cid,courseId,course,semester,collage,collageName,uid;
    long timestamp;


    //constructor


    public ModelSemester() {
    }

    public ModelSemester(String cid, String courseId, String course, String semester, String collage, String collageName, String uid, long timestamp) {
        this.cid = cid;
        this.courseId = courseId;
        this.course = course;
        this.semester = semester;
        this.collage = collage;
        this.collageName = collageName;
        this.uid = uid;
        this.timestamp = timestamp;
    }


    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
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

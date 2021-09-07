package com.ranawat.collagenotes.Model;

public class ModelSubject {

    //variable
    String cid,uid, courseId, course, semesterId,semesterTitle,subject;
    Long timestamp;




    //constructor

    public ModelSubject() {

    }

    public ModelSubject(String cid,String uid, String courseId, String course, String semesterId, String semesterTitle, String subject, Long timestamp) {
        this.cid = cid;
        this.uid = uid;
        this.courseId = courseId;
        this.course = course;
        this.semesterId = semesterId;
        this.semesterTitle = semesterTitle;
        this.subject = subject;
        this.timestamp = timestamp;
    }


    //getter and setter


    public String getCid() {
        return cid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(String semesterId) {
        this.semesterId = semesterId;
    }

    public String getSemesterTitle() {
        return semesterTitle;
    }

    public void setSemesterTitle(String semesterTitle) {
        this.semesterTitle = semesterTitle;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}

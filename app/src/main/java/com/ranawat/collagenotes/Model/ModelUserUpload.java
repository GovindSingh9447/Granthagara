package com.ranawat.collagenotes.Model;

public class ModelUserUpload {

    String uid,title,descriptions,id,semesterTitle,subject,courseName,collageTitle,url;
    long timestamp;

    public ModelUserUpload() {
    }

    public ModelUserUpload(String uid, String title, String descriptions, String id, String semesterTitle, String subject, String courseName, String collageTitle, String url, long timestamp) {
        this.uid = uid;
        this.title = title;
        this.descriptions = descriptions;
        this.id = id;
        this.semesterTitle = semesterTitle;
        this.subject = subject;
        this.courseName = courseName;
        this.collageTitle = collageTitle;
        this.url = url;
        this.timestamp = timestamp;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCollageTitle() {
        return collageTitle;
    }

    public void setCollageTitle(String collageTitle) {
        this.collageTitle = collageTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

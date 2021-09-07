package com.ranawat.collagenotes.Model;

public class ModelNotes {

    String uid, id, title, subjectId, Subject,descriptions ,url,courseName,collageTitle , senderName;
    long timestamp;

    public ModelNotes() {
    }

    public ModelNotes(String uid, String id, String title, String senderName, String collageTitle, String subjectId, String subject, String descriptions, String url, String courseName, long timestamp) {
        this.uid = uid;
        this.id = id;
        this.title = title;
        this.subjectId = subjectId;
        this.senderName = senderName;
        this.Subject = subject;
        this.descriptions = descriptions;
        this.url = url;
        this.courseName = courseName;
        this.timestamp = timestamp;
        this.collageTitle= collageTitle;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getCollageTitle() {
        return uid;
    }

    public void setCollageTitle(String collageTitle) {
        this.collageTitle = collageTitle;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

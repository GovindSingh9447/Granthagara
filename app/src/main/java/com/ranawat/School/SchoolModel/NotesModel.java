package com.ranawat.School.SchoolModel;

public class NotesModel {

    String id,title,sourceName,pdf,subject_id;

    public NotesModel() {
    }

    public NotesModel(String id, String title, String sourceName, String pdf, String subject_id) {
        this.id = id;
        this.title = title;
        this.sourceName = sourceName;
        this.pdf = pdf;
        this.subject_id = subject_id;
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

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }
}

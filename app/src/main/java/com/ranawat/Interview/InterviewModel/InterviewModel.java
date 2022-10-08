package com.ranawat.Interview.InterviewModel;

public class InterviewModel {

    String Id, title, img;

    public InterviewModel() {
    }

    public InterviewModel(String id, String title, String img) {
        Id = id;
        this.title = title;
        this.img = img;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

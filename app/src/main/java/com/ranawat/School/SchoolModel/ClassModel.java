package com.ranawat.School.SchoolModel;

public class ClassModel {

    String Id, title, img;

    public ClassModel() {
    }

    public ClassModel(String id, String title, String img) {
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

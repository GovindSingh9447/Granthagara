package com.ranawattech.School.SchoolModel;

public class SubjectModel {
    String id,title,img,classs,f_id;

    public SubjectModel() {
    }

    public SubjectModel(String id, String title, String img, String classs, String f_id) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.classs = classs;
        this.f_id = f_id;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }
}

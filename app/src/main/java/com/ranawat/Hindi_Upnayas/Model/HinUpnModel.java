package com.ranawat.Hindi_Upnayas.Model;

public class HinUpnModel {

    String author, title,id, img;

    public HinUpnModel() {
    }

    public HinUpnModel(String author, String title, String id, String img) {
        this.author = author;
        this.title = title;
        this.id = id;
        this.img = img;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

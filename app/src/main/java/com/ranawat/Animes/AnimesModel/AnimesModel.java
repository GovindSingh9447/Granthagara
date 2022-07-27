package com.ranawat.Animes.AnimesModel;

public class AnimesModel {
    String title, author,img,chapter,status,artist;

    public AnimesModel() {
    }

    public AnimesModel(String title, String author, String img, String chapter, String status, String artist) {
        this.title = title;
        this.author = author;
        this.img = img;
        this.chapter = chapter;
        this.status = status;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}

package com.ranawat.Animes.AnimesModel;

import java.util.List;

public class AnimesModel {
    String animeName,artist,author,status,img,total_chapters;
    String anime;

    public AnimesModel() {
    }

    public AnimesModel(String animeName, String artist, String author, String status, String img, String total_chapters, String anime) {
        this.animeName = animeName;
        this.artist = artist;
        this.author = author;
        this.status = status;
        this.img = img;
        this.total_chapters = total_chapters;
        this.anime = anime;
    }

    public String getAnimeName() {
        return animeName;
    }

    public void setAnimeName(String animeName) {
        this.animeName = animeName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTotal_chapters() {
        return total_chapters;
    }

    public void setTotal_chapters(String total_chapters) {
        this.total_chapters = total_chapters;
    }

    public String getAnime() {
        return anime;
    }

    public void setAnime(String anime) {
        this.anime = anime;
    }
}

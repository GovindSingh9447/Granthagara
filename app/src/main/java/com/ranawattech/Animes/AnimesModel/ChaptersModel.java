package com.ranawattech.Animes.AnimesModel;

public class ChaptersModel {

    String chapter, dec, like, animeName, cid,animeId;


    public ChaptersModel() {
    }

    public ChaptersModel(String chapter, String dec, String like, String animeName, String cid, String animeId) {
        this.chapter= chapter;
        this.dec = dec;
        this.like = like;
        this.animeName = animeName;
        this.cid = cid;
        this.animeId = animeId;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getAnimeName() {
        return animeName;
    }

    public void setAnimeName(String animeName) {
        this.animeName = animeName;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getAnimeId() {
        return animeId;
    }

    public void setAnimeId(String animeId) {
        this.animeId = animeId;
    }
}

package com.ranawattech.Animes.AnimesModel;

public class AnimePreviewModel {

    String anime, animeId, chapter,chapterId,url_1;


    public AnimePreviewModel() {
    }

    public AnimePreviewModel(String anime, String animeId, String chapter, String chapterId, String url_1) {
        this.anime = anime;
        this.animeId = animeId;
        this.chapter = chapter;
        this.chapterId = chapterId;
        this.url_1 = url_1;
    }

    public String getAnime() {
        return anime;
    }

    public void setAnime(String anime) {
        this.anime = anime;
    }

    public String getAnimeId() {
        return animeId;
    }

    public void setAnimeId(String animeId) {
        this.animeId = animeId;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getUrl_1() {
        return url_1;
    }

    public void setUrl_1(String url_1) {
        this.url_1 = url_1;
    }
}

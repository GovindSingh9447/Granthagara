package com.ranawattech.entrance.EntranceModel;

public class EntranceVModel {

    String id,title,source,img,pdf,f_id;

    public EntranceVModel() {

    }

    public EntranceVModel(String id, String title, String source, String img, String pdf, String f_id) {
        this.id = id;
        this.title = title;
        this.source = source;
        this.img = img;
        this.pdf = pdf;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }
}

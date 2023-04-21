package com.ranawattech.Slider.Model;

public class ModelSlider {

    //variable
    String sliderId, color, text, img;
    long timestamp;

    public ModelSlider() {
    }

    public ModelSlider(String sliderId, String color, String text, String img, long timestamp) {
        this.sliderId = sliderId;
        this.color = color;
        this.text = text;
        this.img = img;
        this.timestamp = timestamp;
    }

    public String getSliderId() {
        return sliderId;
    }

    public void setSliderId(String sliderId) {
        this.sliderId = sliderId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

package com.ranawattech.collagenotes.Model;

public class ModelMessages {

    String cid, message, sendername, uid;
    long timestamp;

    public ModelMessages() {
    }

    public ModelMessages(String cid, String message, String sendername, String uid, long timestamp) {
        this.cid = cid;
        this.message = message;
        this.sendername = sendername;
        this.uid = uid;
        this.timestamp = timestamp;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

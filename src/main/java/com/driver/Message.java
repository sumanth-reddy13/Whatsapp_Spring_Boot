package com.driver;

import java.util.Date;

public class Message {
    private int id;
    private String content;
    private String timestamp;

    public Message(int id, String content, String timestamp) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return this.content;
    }

    public int getId() {
        return this.id;
    }

    public String  getTimestamp() {
        return this.timestamp;
    }
}

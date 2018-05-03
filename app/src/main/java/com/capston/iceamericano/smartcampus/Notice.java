package com.capston.iceamericano.smartcampus;

/**
 * Created by cksdn on 2018-03-18.
 */

public class Notice {

    String writer, title, time;

    public Notice(String writer, String title, String time) {
        this.writer = writer;
        this.title = title;
        this.time = time;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}

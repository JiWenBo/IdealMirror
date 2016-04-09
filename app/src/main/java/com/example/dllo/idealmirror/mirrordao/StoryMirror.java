package com.example.dllo.idealmirror.mirrordao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "STORY_MIRROR".
 */
public class StoryMirror {

    private Long id;
    private String picimg;
    private String title;
    private String type;

    public StoryMirror() {
    }

    public StoryMirror(Long id) {
        this.id = id;
    }

    public StoryMirror(Long id, String picimg, String title, String type) {
        this.id = id;
        this.picimg = picimg;
        this.title = title;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPicimg() {
        return picimg;
    }

    public void setPicimg(String picimg) {
        this.picimg = picimg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

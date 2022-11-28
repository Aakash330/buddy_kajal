package com.studybuddy.pc.brainmate.model;

public class ReferenceMaterialModel {

    private String Title;
    private String url;

    public ReferenceMaterialModel(int i, String a,String url) {
        Title=a;
        this.url=url;

    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

package com.studybuddy.pc.brainmate.model.fun_data_list;

import java.util.Comparator;

public class FunForList {

    private String publisher_id;
    private String date;
    private String class1;
    private String subject;
    private String series;
    private String id;
    private String title;
    private String type;
    private String url;
    private String isFree;

    /*Comparator for sorting the list by order id*/
    public static Comparator<FunForList> getByType = new Comparator<FunForList>() {
        @Override
        public int compare(FunForList o1, FunForList o2) {
            int id_1 = Integer.parseInt(o1.getType() != null ? o1.getType() : "0");
            int id_2 = Integer.parseInt(o2.getType() != null ? o2.getType() : "0");
            /*For descending order*/
            return id_1 - id_2;
            /*For ascending order*/
            //id_1-id_2;
        }
    };

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public String getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(String publisher_id) {
        this.publisher_id = publisher_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClass1() {
        return class1;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

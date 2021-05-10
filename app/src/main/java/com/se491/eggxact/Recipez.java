package com.se491.eggxact;

import java.util.List;

public class Recipez {
    String title;
    String author;
    String summary;

    List<String> ingredients;

    public Recipez(String tit, String aut, String sum, List<String> ing){
        this.title = tit;
        this.author = aut;
        this.summary = sum;
        this.ingredients = ing;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

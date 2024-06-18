package com.example.time.model;

import android.content.Context;

public class Action {
    int id;
    String Title;

    public Action(int id,String title) {
        this.id = id;
        Title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Context useContent(Context cur){
        return LibActions.useContent(Title, cur);
    }
}

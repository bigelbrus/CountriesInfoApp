package com.example.countriesinfoapp.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Currency{

    @SerializedName("name")
    @Expose
    private String name;

    public Currency(String name){
        this.name = name;
    }
    public Currency(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

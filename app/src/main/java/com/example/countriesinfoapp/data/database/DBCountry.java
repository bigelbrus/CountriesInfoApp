package com.example.countriesinfoapp.data.database;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DBCountry extends RealmObject {
    @PrimaryKey
    private String name;

    private String capital;
    private String currency;
    private String region;
    private int population;
    private String flag;


    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DBCountry(String name){
        this.name = name;
    }

    public DBCountry(){}

    public void setValues(String capital
            ,String currency
            ,String region
            ,int population
            ,String flag){
        this.capital = capital;
        this.currency = currency;
        this.region = region;
        this.population = population;
        this.flag = flag;
    }
}

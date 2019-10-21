package com.example.countriesinfoapp.data.network;

import androidx.lifecycle.LiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JSONCountriesApi {
    @GET("/rest/v2/all?fields=name")
    Call<List<CountryNames>> getAllCountries();

    @GET("/rest/v2/name/{name}")
    Call<List<Country>> getCountryByName(@Path("name") String name);
}

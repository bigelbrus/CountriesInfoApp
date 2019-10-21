package com.example.countriesinfoapp.ui.details;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.countriesinfoapp.data.DataRepository;
import com.example.countriesinfoapp.data.network.Country;

public class DetailViewModel extends ViewModel {
    private DataRepository mRepository;
    private LiveData<Country> mCountry;

    public DetailViewModel(DataRepository repository, String countryName){
        mRepository = repository;
        mCountry = mRepository.getCountryByName(countryName);
    }

    public LiveData<Country> getCountry(){
        return mCountry;
    }
}

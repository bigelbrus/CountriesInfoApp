package com.example.countriesinfoapp.ui.details;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.countriesinfoapp.data.DataRepository;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private DataRepository mRepository;
    private String mCountryName;

    public DetailViewModelFactory(DataRepository repository, String countryName){
        mRepository = repository;
        mCountryName = countryName;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailViewModel(mRepository,mCountryName) ;
    }
}

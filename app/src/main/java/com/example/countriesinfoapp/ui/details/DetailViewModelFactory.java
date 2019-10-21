package com.example.countriesinfoapp.ui.details;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.countriesinfoapp.data.DataRepository;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private DataRepository mRepository;
    private String mCountryName;
    private LifecycleOwner owner;

    public DetailViewModelFactory(DataRepository repository, String countryName){
        mRepository = repository;
        mCountryName = countryName;
        this.owner = owner;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailViewModel(mRepository,mCountryName) ;
    }
}

package com.example.countriesinfoapp.ui.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.example.countriesinfoapp.R;
import com.example.countriesinfoapp.data.network.Country;
import com.example.countriesinfoapp.data.network.Currency;
import com.example.countriesinfoapp.utilities.FactoryUtils;

public class DetailActivity extends AppCompatActivity {
    private DetailViewModel mViewModel;

    private TextView mCountryName;
    private TextView mCurrency;
    private TextView mRegion;
    private TextView mPopulation;
    private TextView mCapital;
    private ImageView mFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String countryName = getIntent().getStringExtra(Intent.EXTRA_COMPONENT_NAME);
        initViews();

        DetailViewModelFactory factory = FactoryUtils.getDetailViewModelFactory(this, countryName);
        mViewModel = new ViewModelProvider(this, factory).get(DetailViewModel.class);
        mViewModel.getCountry().observe(this,country -> {
            populateUI(country);
        });

    }

    private void initViews() {
        mCountryName = findViewById(R.id.tv_country_name_detail);
        mCurrency = findViewById(R.id.tv_currency_detail);
        mRegion = findViewById(R.id.tv_region_detail);
        mPopulation = findViewById(R.id.tv_popuation_detail);
        mCapital = findViewById(R.id.tv_capital_detail);
        mFlag = findViewById(R.id.iv_flag_detail);
    }

    private void populateUI(Country country) {
        mCountryName.setText(country.getName());
        mCurrency.setText("");
        for (Currency c : country.getCurrencies()) {
            mCurrency.append(c.getName());
        }
        mRegion.setText(country.getRegion());
        mPopulation.setText(String.valueOf(country.getPopulation()));
        mCapital.setText(country.getCapital());
        SvgLoader.pluck().with(this)
                .load(country.getFlag(),mFlag);

    }

    @Override
    protected void onDestroy() {
        SvgLoader.pluck().close();
        super.onDestroy();
    }
}

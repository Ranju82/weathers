package com.ranjutech.weather.presenter;

import android.location.Location;

import com.ranjutech.weather.model.Address;
import com.ranjutech.weather.model.Forecast;
import com.ranjutech.weather.model.History;
import com.ranjutech.weather.webservice.ApiInterface;
import com.ranjutech.weather.webservice.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastPresenter {

    private View view;
    private Location loc;
    private Address address;
    private List<Forecast.DailyForecast> dailyForecastList;
    private List<History> historyList;

    public ForecastPresenter(View view, Location loc){
        this.view=view;
        this.loc=loc;
        dailyForecastList =new ArrayList<>();
        historyList=new ArrayList<>();
        getAddress();
    }

    private void getAddress() {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<Address> call = apiInterface.getAddress(loc.getLatitude()+","+loc.getLongitude());
        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                address=response.body();
                getForecast(response.body().getKey());
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                view.onError();
            }
        });
    }

    private void getForecast(String key) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<Forecast> call=apiInterface.getForecast(key);
        call.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                dailyForecastList.clear();
                dailyForecastList.addAll(response.body().getDailyForecasts());
                getHistory(key);
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                view.onError();
            }
        });
    }

    public void getHistory(String key){
        ApiInterface apiInterface= ServiceGenerator.createService(ApiInterface.class);
        Call<List<History>> call=apiInterface.getHistory(key);
        call.enqueue(new Callback<List<History>>() {
            @Override
            public void onResponse(Call<List<History>> call, Response<List<History>> response) {
                historyList.clear();
                historyList.addAll(response.body());
                view.setValues(address,dailyForecastList,historyList);
            }

            @Override
            public void onFailure(Call<List<History>> call, Throwable t) {

            }
        });
    }

    public void onRefresh(){
        getAddress();
    }

    public interface View {
        void setValues(Address address, List<Forecast.DailyForecast> dailyForecastList,List<History> historyList);
        void onError();
    }
}

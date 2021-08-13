package com.ranjutech.weather.webservice;

import com.ranjutech.weather.model.Address;
import com.ranjutech.weather.model.Forecast;
import com.ranjutech.weather.model.History;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("locations/v1/cities/geoposition/search")
    Call<Address> getAddress(@Query("q") String latlon);
    @GET("forecasts/v1/daily/5day/{lockey}")
    Call<Forecast> getForecast(@Path("lockey") String lockey);
    @GET("currentconditions/v1/{lockey}/historical/24")
    Call<List<History>> getHistory(@Path("lockey") String lockey);
}

package com.ranjutech.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.ranjutech.weather.databinding.ActivityMainBinding;
import com.ranjutech.weather.model.Address;
import com.ranjutech.weather.model.Forecast;
import com.ranjutech.weather.model.History;
import com.ranjutech.weather.presenter.ForecastPresenter;
import com.ranjutech.weather.utils.Image;
import com.ranjutech.weather.utils.constant;
import com.ranjutech.weather.webservice.ApiInterface;
import com.ranjutech.weather.webservice.ServiceGenerator;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ForecastPresenter.View {

    private ForecastPresenter forecastPresenter;
    private FusedLocationProviderClient fusedLocationClient;
    private Location mCurrentLocation;
    Boolean requestingLocationUpdates = false;
    LocationRequest locationRequest;

    private LocationCallback locationCallback;

    private ActivityMainBinding binding;

    DataPoint[] dataPoint;
    LineGraphSeries<DataPoint> series;
    Image image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Weather);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        onProgress(true);
        image=new Image();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            return;
        } else {
            requestingLocationUpdates = true;
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        mCurrentLocation = location;
                    }
                }
            });
        }

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000000);
        locationRequest.setFastestInterval(500000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                startLocationUpdates();
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(MainActivity.this,
                                1);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    if(mCurrentLocation!=location) {
                        mCurrentLocation = location;
                    }
                }
                forecastPresenter=new ForecastPresenter(MainActivity.this,mCurrentLocation);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (requestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }


    @Override
    public void setValues(Address address, List<Forecast.DailyForecast> dailyForecastList, List<History> historyList) {
        binding.addressTextView.setText(address.getAddress()+", "+address.getArea().getAreaName()+", "+address.getCountry().getCountryName());
        if(dailyForecastList.size()!=0){
            for(int i=0;i<dailyForecastList.size();i++){
                switch (i){
                    case 0:
                        Forecast.DailyForecast dailyForecast=dailyForecastList.get(0);
                        binding.max0TextView.setText(fehrenToCelcius(dailyForecast.getTemp().getMax().getMaxValue())+"\u2103");
                        binding.min0TextView.setText(fehrenToCelcius(dailyForecast.getTemp().getMin().getMinValue())+"\u2103");
                        binding.dayPhrase.setText(dailyForecast.getDay().getIconPhrase());
                        binding.nightPhrase.setText(dailyForecast.getNight().getIconPhrase());
                        setImage(image.getImage(dailyForecast.getDay().getIcon()),binding.day0ImageView);
                        break;
                    case 1:
                        Forecast.DailyForecast dailyForecast1=dailyForecastList.get(1);
                        binding.max1TextView.setText(fehrenToCelcius(dailyForecast1.getTemp().getMax().getMaxValue())+"\u2103");
                        binding.day1TextView.setText(getDay(dailyForecast1.getDate()));
                        setImage(image.getImage(dailyForecast1.getDay().getIcon()),binding.day1ImageView);
                        break;
                    case 2:
                        Forecast.DailyForecast dailyForecast2=dailyForecastList.get(2);
                        binding.max2TextView.setText(fehrenToCelcius(dailyForecast2.getTemp().getMax().getMaxValue())+"\u2103");
                        binding.day2TextView.setText(getDay(dailyForecast2.getDate()));
                        setImage(image.getImage(dailyForecast2.getDay().getIcon()),binding.day2ImageView);
                        break;
                    case 3:
                        Forecast.DailyForecast dailyForecast3=dailyForecastList.get(3);
                        binding.max3TextView.setText(fehrenToCelcius(dailyForecast3.getTemp().getMax().getMaxValue())+"\u2103");
                        binding.day3TextView.setText(getDay(dailyForecast3.getDate()));
                        setImage(image.getImage(dailyForecast3.getDay().getIcon()),binding.day3ImageView);
                        break;
                    case 4:
                        Forecast.DailyForecast dailyForecast4=dailyForecastList.get(4);
                        binding.max4TextView.setText(fehrenToCelcius(dailyForecast4.getTemp().getMax().getMaxValue())+"\u2103");
                        binding.day4TextView.setText(getDay(dailyForecast4.getDate()));
                        setImage(image.getImage(dailyForecast4.getDay().getIcon()),binding.day4ImageView);
                        break;
                }
            }
        }
        int historySize=historyList.size();
        if(historySize!=0){
            dataPoint=new DataPoint[historySize];
            String[] labels=new String[historySize];
            for(int i=0;i<historySize;i++){
                History history=historyList.get(i);
                String temp=history.getTempHis().getMetric().getTemp();
                labels[i]=String.valueOf(i);
                dataPoint[i]=new DataPoint(i,Double.valueOf(temp));
            }
            series=new LineGraphSeries<>(dataPoint);
            //series.setTitle("In Celcius");
            binding.graphView.addSeries(series);
            binding.graphView.setTitle("Last 24hr Temperature");
            //graphView.getLegendRenderer().setVisible(true);
            //graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
            StaticLabelsFormatter totalStaticLabelsFormatter = new StaticLabelsFormatter(binding.graphView);
            totalStaticLabelsFormatter.setHorizontalLabels(labels);
            binding.graphView.getGridLabelRenderer().setLabelFormatter(totalStaticLabelsFormatter);
        }
        onProgress(false);
    }

    @Override
    public void onError() {

    }

    public void onProgress(Boolean bool){
        if(bool){
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.constraintLayout.setVisibility(View.GONE);
        }else{
            binding.progressBar.setVisibility(View.GONE);
            binding.constraintLayout.setVisibility(View.VISIBLE);
        }
    }

    public String fehrenToCelcius(String fehren){
        Double fehrenInDouble=Double.parseDouble(fehren);
        Double celciusInDouble=(fehrenInDouble-32)/1.8;
        return String.valueOf(Math.round(celciusInDouble*10)/10);
    }

    public String getDay(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH);
        Date myDate = null;
        try {
            myDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.applyPattern("EEE, yyyy MMM d");
        return(sdf.format(myDate));
    }

    public void setImage(String url, ImageView imageView){
        Glide
                .with(this)
                .load(url)
                .into(imageView);
    }
}
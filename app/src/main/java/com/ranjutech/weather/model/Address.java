package com.ranjutech.weather.model;

import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("Key")
    private String key;
    @SerializedName("LocalizedName")
    private String address;
    @SerializedName("AdministrativeArea")
    Area area=new Area();
    @SerializedName("Country")
    Country country=new Country();

    public String getKey() {
        return key;
    }

    public String getAddress() {
        return address;
    }

    public Area getArea() {
        return area;
    }

    public Country getCountry() {
        return country;
    }

    public class Area{
        @SerializedName("LocalizedName")
        private String areaName;

        public String getAreaName() {
            return areaName;
        }
    }

    public class Country{
        @SerializedName("LocalizedName")
        private String countryName;

        public String getCountryName() {
            return countryName;
        }
    }
}

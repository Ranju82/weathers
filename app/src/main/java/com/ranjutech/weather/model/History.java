package com.ranjutech.weather.model;

import com.google.gson.annotations.SerializedName;

public class History {

    @SerializedName("Temperature")
    private TempHis tempHis=new TempHis();

    public TempHis getTempHis() {
        return tempHis;
    }

    public class TempHis{
        @SerializedName("Metric")
        private Metric metric=new Metric();

        public Metric getMetric() {
            return metric;
        }
    }

    public class Metric{
        @SerializedName("Value")
        private String temp;

        public String getTemp() {
            return temp;
        }
    }
}

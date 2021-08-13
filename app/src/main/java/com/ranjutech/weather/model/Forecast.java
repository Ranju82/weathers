package com.ranjutech.weather.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Forecast {
    @SerializedName("DailyForecasts")
    private List<DailyForecast> dailyForecasts=new ArrayList<>();

    public List<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }

    public class DailyForecast {
        @SerializedName("Date")
        private String date;
        @SerializedName("Temperature")
        private Temp temp=new Temp();
        @SerializedName("Day")
        private Day day=new Day();
        @SerializedName("Night")
        private Night night=new Night();

        public Day getDay() {
            return day;
        }

        public Night getNight() {
            return night;
        }

        public String getDate() {
            return date;
        }

        public Temp getTemp() {
            return temp;
        }

    }
    public class Day {
        @SerializedName("Icon")
        private int icon;
        @SerializedName("IconPhrase")
        private String iconPhrase;

        public int getIcon() {
            return icon;
        }

        public String getIconPhrase() {
            return iconPhrase;
        }
    }

    public class Night {
        @SerializedName("Icon")
        private int icon;
        @SerializedName("IconPhrase")
        private String iconPhrase;

        public int getIcon() {
            return icon;
        }

        public String getIconPhrase() {
            return iconPhrase;
        }
    }

    public class Temp {
        @SerializedName("Minimum")
        private Min min=new Min();
        @SerializedName("Maximum")
        private Max max=new Max();

        public Min getMin() {
            return min;
        }

        public Max getMax() {
            return max;
        }

    }
    public class Min {
        @SerializedName("Value")
        private String minValue;

        public String getMinValue() {
            return minValue;
        }
    }

    public class Max {
        @SerializedName("Value")
        private String maxValue;

        public String getMaxValue() {
            return maxValue;
        }
    }
}

package com.ranjutech.weather.utils;

import java.util.HashMap;
import java.util.Map;

public class Image {
    Map<Integer,String> images;
    public Image(){
        images=new HashMap<>();
        images.put(1,"https://developer.accuweather.com/sites/default/files/01-s.png");
        images.put(2,"https://developer.accuweather.com/sites/default/files/02-s.png");
        images.put(3,"https://developer.accuweather.com/sites/default/files/03-s.png");
        images.put(4,"https://developer.accuweather.com/sites/default/files/04-s.png");
        images.put(5,"https://developer.accuweather.com/sites/default/files/05-s.png");
        images.put(6,"https://developer.accuweather.com/sites/default/files/06-s.png");
        images.put(7,"https://developer.accuweather.com/sites/default/files/07-s.png");
        images.put(8,"https://developer.accuweather.com/sites/default/files/08-s.png");
        images.put(9,"https://developer.accuweather.com/sites/default/files/09-s.png");
        images.put(10,"https://developer.accuweather.com/sites/default/files/10-s.png");
        images.put(11,"https://developer.accuweather.com/sites/default/files/11-s.png");
        images.put(12,"https://developer.accuweather.com/sites/default/files/12-s.png");
        images.put(13,"https://developer.accuweather.com/sites/default/files/13-s.png");
        images.put(14,"https://developer.accuweather.com/sites/default/files/14-s.png");
        images.put(15,"https://developer.accuweather.com/sites/default/files/15-s.png");
        images.put(16,"https://developer.accuweather.com/sites/default/files/16-s.png");
        images.put(17,"https://developer.accuweather.com/sites/default/files/17-s.png");
        images.put(18,"https://developer.accuweather.com/sites/default/files/18-s.png");
        images.put(19,"https://developer.accuweather.com/sites/default/files/19-s.png");
        images.put(20,"https://developer.accuweather.com/sites/default/files/20-s.png");
        images.put(21,"https://developer.accuweather.com/sites/default/files/21-s.png");
        images.put(22,"https://developer.accuweather.com/sites/default/files/22-s.png");
        images.put(23,"https://developer.accuweather.com/sites/default/files/23-s.png");
        images.put(24,"https://developer.accuweather.com/sites/default/files/24-s.png");
        images.put(25,"https://developer.accuweather.com/sites/default/files/25-s.png");
        images.put(26,"https://developer.accuweather.com/sites/default/files/26-s.png");
        images.put(27,"https://developer.accuweather.com/sites/default/files/27-s.png");
        images.put(28,"https://developer.accuweather.com/sites/default/files/28-s.png");
        images.put(29,"https://developer.accuweather.com/sites/default/files/29-s.png");
        images.put(30,"https://developer.accuweather.com/sites/default/files/30-s.png");
        images.put(31,"https://developer.accuweather.com/sites/default/files/31-s.png");
        images.put(32,"https://developer.accuweather.com/sites/default/files/32-s.png");
        images.put(33,"https://developer.accuweather.com/sites/default/files/33-s.png");
        images.put(34,"https://developer.accuweather.com/sites/default/files/34-s.png");
        images.put(35,"https://developer.accuweather.com/sites/default/files/35-s.png");
        images.put(36,"https://developer.accuweather.com/sites/default/files/36-s.png");
        images.put(37,"https://developer.accuweather.com/sites/default/files/37-s.png");
        images.put(38,"https://developer.accuweather.com/sites/default/files/38-s.png");
        images.put(39,"https://developer.accuweather.com/sites/default/files/39-s.png");
        images.put(40,"https://developer.accuweather.com/sites/default/files/40-s.png");
        images.put(41,"https://developer.accuweather.com/sites/default/files/41-s.png");
        images.put(42,"https://developer.accuweather.com/sites/default/files/42-s.png");
        images.put(43,"https://developer.accuweather.com/sites/default/files/43-s.png");
        images.put(44,"https://developer.accuweather.com/sites/default/files/44-s.png");
    }
    public String getImage(int num){
        return images.get(num);
    }
}

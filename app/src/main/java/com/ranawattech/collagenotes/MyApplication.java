package com.ranawattech.collagenotes;

import android.app.Application;
import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static final String formatTimestamp(long timestamp){
        Calendar calendar=Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(timestamp);

        //formate dd/mm/yy
        String  date = DateFormat.format("dd/mm/yy",calendar).toString();
        return date;
    }
}

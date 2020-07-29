package com.example.myfinancemanager;

import android.app.Application;

import com.example.myfinancemanager.utility.TypefaceUtil;
import com.idescout.sql.SqlScoutServer;

import net.danlew.android.joda.JodaTimeAndroid;

public class PFFinanceApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        SqlScoutServer.create(this, getPackageName());
        //TypefaceUtil.overrideFont(this,"SERIF","fonts/EstedadFDMedium.ttf");
    }
}

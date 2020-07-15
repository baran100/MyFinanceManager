package com.example.myfinancemanager.activity.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.example.myfinancemanager.R;
import com.example.myfinancemanager.utility.HomePackageTabAdapter;
import com.google.android.material.tabs.TabLayout;

public class HomeViewModel extends BaseViewModel {



    public HomeViewModel(@NonNull Application application) {
        super(application);
        setNavigationDrawerId(R.id.nav_main);
        setTitle(R.string.menu_title_dashboard);


    }



}

package com.example.myfinancemanager.activity.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.myfinancemanager.R;

public class StatisticsViewModel extends BaseViewModel {

    public StatisticsViewModel(@NonNull Application application) {
        super(application);
        setNavigationDrawerId(R.id.nav_statistics);
        setTitle(R.string.menu_title_statistics);

    }

}

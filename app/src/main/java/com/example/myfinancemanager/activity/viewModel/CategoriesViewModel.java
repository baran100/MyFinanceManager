package com.example.myfinancemanager.activity.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.myfinancemanager.R;

public class CategoriesViewModel extends BaseViewModel {

    public CategoriesViewModel(@NonNull Application application) {
        super(application);
        setNavigationDrawerId(R.id.nav_categories);
        setTitle(R.string.menu_title_categories);

    }

}

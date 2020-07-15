package com.example.myfinancemanager.activity.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.myfinancemanager.R;

public class TagsViewModel extends BaseViewModel {

    public TagsViewModel(@NonNull Application application) {
        super(application);
        setNavigationDrawerId(R.id.nav_tags);
        setTitle(R.string.menu_title_tags);

    }

}

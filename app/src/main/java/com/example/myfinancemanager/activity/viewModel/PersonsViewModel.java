package com.example.myfinancemanager.activity.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.myfinancemanager.R;

public class PersonsViewModel extends BaseViewModel {

    public PersonsViewModel(@NonNull Application application) {
        super(application);
        setNavigationDrawerId(R.id.nav_persons);
        setTitle(R.string.menu_title_person);

    }

}

package com.example.myfinancemanager.activity.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.myfinancemanager.R;

public class AddEditViewModel extends BaseViewModel {

    public AddEditViewModel(@NonNull Application application) {
        super(application);
        setTitle(R.string.add_transaction);
        setNavigationDrawerId(null);

    }

    @Override
    public boolean showDrawer() {
        return false;
    }

    @Override
    public boolean showArrowBack() {
        return true;
    }
}

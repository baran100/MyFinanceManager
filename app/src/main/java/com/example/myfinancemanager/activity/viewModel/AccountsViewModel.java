package com.example.myfinancemanager.activity.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.myfinancemanager.R;

public class AccountsViewModel extends BaseViewModel {

    public AccountsViewModel(@NonNull Application application) {
        super(application);
        setNavigationDrawerId(R.id.nav_accounts);
        setTitle(R.string.menu_title_accounts);

    }

}

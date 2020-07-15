package com.example.myfinancemanager.activity.account;

import android.os.Bundle;
import com.example.myfinancemanager.BaseActivity;
import com.example.myfinancemanager.R;
import com.example.myfinancemanager.activity.viewModel.AccountsViewModel;
import com.example.myfinancemanager.activity.viewModel.BaseViewModel;

public class AccountsActivity extends BaseActivity {
    AccountsViewModel viewModel;

    @Override
    protected Class<? extends BaseViewModel> getViewModelClass() {
        return AccountsViewModel.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_accounts);
        viewModel = (AccountsViewModel) super.viewModel;
    }
}
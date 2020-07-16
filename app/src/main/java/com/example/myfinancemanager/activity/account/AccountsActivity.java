package com.example.myfinancemanager.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myfinancemanager.BaseActivity;
import com.example.myfinancemanager.R;
import com.example.myfinancemanager.activity.add.AddEditAccountActivity;
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

        addFab(R.layout.fab_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountsActivity.this, AddEditAccountActivity.class);
                startActivity(intent);
            }
        });
    }
}
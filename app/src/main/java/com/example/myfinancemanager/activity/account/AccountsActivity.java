package com.example.myfinancemanager.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfinancemanager.BaseActivity;
import com.example.myfinancemanager.R;
import com.example.myfinancemanager.activity.add.AddEditAccountActivity;
import com.example.myfinancemanager.activity.viewModel.AccountsViewModel;
import com.example.myfinancemanager.activity.viewModel.BaseViewModel;
import com.example.myfinancemanager.adapter.AccountsAdapter;

public class AccountsActivity extends BaseActivity {
    AccountsViewModel viewModel;
    private RecyclerView recyclerView;
    private AccountsAdapter adapter;

    @Override
    protected Class<? extends BaseViewModel> getViewModelClass() {
        return AccountsViewModel.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_accounts);
        viewModel = (AccountsViewModel) super.viewModel;

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        adapter = new AccountsAdapter();
        recyclerView.setAdapter(adapter);

        addFab(R.layout.fab_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountsActivity.this, AddEditAccountActivity.class);
                startActivity(intent);
            }
        });
    }
}
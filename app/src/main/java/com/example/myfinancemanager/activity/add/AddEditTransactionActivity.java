package com.example.myfinancemanager.activity.add;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myfinancemanager.BaseActivity;
import com.example.myfinancemanager.R;
import com.example.myfinancemanager.activity.viewModel.AddEditViewModel;
import com.example.myfinancemanager.activity.viewModel.BaseViewModel;

public class AddEditTransactionActivity extends BaseActivity {

    @Override
    protected Class<? extends BaseViewModel> getViewModelClass() {
        return AddEditViewModel.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_add_edit_transaction);

        Toolbar toolbar = findViewById(R.id.toolbar_add);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        addFab(R.layout.fab_don, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddEditTransactionActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard,menu);
        MenuItem menuItemEdit = menu.findItem(R.id.menu_home_tip);
        menuItemEdit.setVisible(false);
        return true;
    }
}
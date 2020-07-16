package com.example.myfinancemanager.activity.category;

import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.myfinancemanager.BaseActivity;
import com.example.myfinancemanager.R;
import com.example.myfinancemanager.activity.add.AddEditCategoryActivity;
import com.example.myfinancemanager.activity.viewModel.BaseViewModel;
import com.example.myfinancemanager.activity.viewModel.CategoriesViewModel;

public class CategoriesActivity extends BaseActivity {

    @Override
    protected Class<? extends BaseViewModel> getViewModelClass() {
        return CategoriesViewModel.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_categories);

        addFab(R.layout.fab_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesActivity.this, AddEditCategoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
package com.example.myfinancemanager.activity.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfinancemanager.BaseActivity;
import com.example.myfinancemanager.R;
import com.example.myfinancemanager.activity.add.AddEditCategoryActivity;
import com.example.myfinancemanager.activity.viewModel.BaseViewModel;
import com.example.myfinancemanager.activity.viewModel.CategoriesViewModel;
import com.example.myfinancemanager.adapter.CategoriesAdapter;

public class CategoriesActivity extends BaseActivity implements CategoriesAdapter.itemEventListener{

    private CategoriesAdapter adapter;
    private RecyclerView recyclerView;


    @Override
    protected Class<? extends BaseViewModel> getViewModelClass() {
        return CategoriesViewModel.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_categories);

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        adapter = new CategoriesAdapter(this);
        recyclerView.setAdapter(adapter);

        addFab(R.layout.fab_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesActivity.this, AddEditCategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(String fullName, int position) {

    }
}
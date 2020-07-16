package com.example.myfinancemanager.activity.tag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myfinancemanager.BaseActivity;
import com.example.myfinancemanager.R;
import com.example.myfinancemanager.activity.add.AddEditTagActivity;
import com.example.myfinancemanager.activity.viewModel.BaseViewModel;
import com.example.myfinancemanager.activity.viewModel.TagsViewModel;

public class TagsActivity extends BaseActivity {

    @Override
    protected Class<? extends BaseViewModel> getViewModelClass() {
        return TagsViewModel.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_tags);

        addFab(R.layout.fab_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TagsActivity.this, AddEditTagActivity.class);
                startActivity(intent);
            }
        });
    }
}
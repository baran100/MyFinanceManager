package com.example.myfinancemanager.activity.person;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myfinancemanager.BaseActivity;
import com.example.myfinancemanager.R;
import com.example.myfinancemanager.activity.add.AddEditPersonActivity;
import com.example.myfinancemanager.activity.add.AddEditTagActivity;
import com.example.myfinancemanager.activity.tag.TagsActivity;
import com.example.myfinancemanager.activity.viewModel.BaseViewModel;
import com.example.myfinancemanager.activity.viewModel.PersonsViewModel;

public class PersonsActivity extends BaseActivity {

    @Override
    protected Class<? extends BaseViewModel> getViewModelClass() {
        return PersonsViewModel.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_persons);

        addFab(R.layout.fab_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonsActivity.this, AddEditPersonActivity.class);
                startActivity(intent);
            }
        });
    }
}
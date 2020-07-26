package com.example.myfinancemanager.activity.person;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfinancemanager.BaseActivity;
import com.example.myfinancemanager.R;
import com.example.myfinancemanager.activity.add.AddEditPersonActivity;
import com.example.myfinancemanager.activity.viewModel.BaseViewModel;
import com.example.myfinancemanager.activity.viewModel.PersonsViewModel;
import com.example.myfinancemanager.adapter.PersonsAdapter;

public class PersonsActivity extends BaseActivity implements PersonsAdapter.itemEventListener {
    public static  RecyclerView recyclerView;
    public static PersonsAdapter adapter;

    @Override
    protected Class<? extends BaseViewModel> getViewModelClass() {
        return PersonsViewModel.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_persons);

        recyclerView=findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        adapter = new PersonsAdapter(this);
        recyclerView.setAdapter(adapter);

        addFab(R.layout.fab_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonsActivity.this, AddEditPersonActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(String fullName, int position) {
        Intent intent = new Intent(PersonsActivity.this,AddEditPersonActivity.class);
        intent.putExtra("ITEM_POSITION",position);
        intent.putExtra("PERSON_NAME",fullName);
        startActivity(intent);
    }
}
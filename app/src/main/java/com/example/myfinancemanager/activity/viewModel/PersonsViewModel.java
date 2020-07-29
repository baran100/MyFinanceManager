package com.example.myfinancemanager.activity.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.myfinancemanager.R;
import com.example.myfinancemanager.domain.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonsViewModel extends BaseViewModel {

    private List<Person> personList= new ArrayList<>();

    public PersonsViewModel(@NonNull Application application) {
        super(application);
        setNavigationDrawerId(R.id.nav_persons);
        setTitle(R.string.menu_title_person);

    }

}

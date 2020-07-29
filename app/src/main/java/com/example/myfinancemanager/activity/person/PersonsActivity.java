package com.example.myfinancemanager.activity.person;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfinancemanager.BaseActivity;
import com.example.myfinancemanager.R;
import com.example.myfinancemanager.activity.add.AddEditPersonActivity;
import com.example.myfinancemanager.activity.viewModel.BaseViewModel;
import com.example.myfinancemanager.activity.viewModel.PersonsViewModel;
import com.example.myfinancemanager.adapter.PersonsAdapter;
import com.example.myfinancemanager.domain.FinanceDatabase;
import com.example.myfinancemanager.domain.access.PersonDao;
import com.example.myfinancemanager.domain.model.Person;

import java.util.List;

public class PersonsActivity extends BaseActivity implements PersonsAdapter.itemEventListener {
    public static  RecyclerView recyclerView;
    public static PersonsAdapter adapter;
    public static final String EXTRA_PERSON = "person";
    private Person person;
    private PersonDao personDao;
    private List<Person> personList;
    private TextView emptyView;

    @Override
    protected Class<? extends BaseViewModel> getViewModelClass() {
        return PersonsViewModel.class;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_persons);
        personDao = FinanceDatabase.getAppDatabase(this).getPersonDao();

        recyclerView=findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        adapter = new PersonsAdapter(this);
        recyclerView.setAdapter(adapter);
        personList = personDao.getAllPerson();
        adapter.addItems(personList);

        addFab(R.layout.fab_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonsActivity.this, AddEditPersonActivity.class);
                startActivity(intent);
                //AddEditPersonDialog dialog = new AddEditPersonDialog();
                //dialog.show(getSupportFragmentManager(),null);
            }
        });

        Bundle bundle = this.getIntent().getExtras();
        person = null;
        if (bundle != null) {
            person = (Person) bundle.getSerializable(EXTRA_PERSON);

            long idPerson = personDao.addPerson(person);
            if (idPerson != -1){
                person.setId(idPerson);
                adapter.addItem(person);
                Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, R.string.not_saved, Toast.LENGTH_SHORT).show();
            }
        }

    }


    @Override
    public void onItemClick(Person person) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AddEditPersonActivity.PTE,person);
        Intent intent = new Intent(this,AddEditPersonActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
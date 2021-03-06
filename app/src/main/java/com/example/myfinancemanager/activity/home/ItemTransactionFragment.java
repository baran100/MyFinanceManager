package com.example.myfinancemanager.activity.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfinancemanager.R;
import com.example.myfinancemanager.adapter.ItemTransactionsAdapter;


public class ItemTransactionFragment extends Fragment {

    private RecyclerView recyclerView;
    private ItemTransactionsAdapter adapter;

    public ItemTransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_transaction, container, false);
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        adapter = new ItemTransactionsAdapter();
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_transaction,menu);
        MenuItem menuItemEdit = menu.findItem(R.id.toolbar_action_edit);
        MenuItem menuItemEdit2 = menu.findItem(R.id.menu_transaction_search);
        menuItemEdit2.setVisible(false);
        menuItemEdit.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
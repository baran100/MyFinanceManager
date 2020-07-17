package com.example.myfinancemanager.activity.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.myfinancemanager.R;

import ir.mirrajabi.persiancalendar.PersianCalendarView;
import ir.mirrajabi.persiancalendar.core.PersianCalendarHandler;
import ir.mirrajabi.persiancalendar.core.models.PersianDate;


public class DashboardFragment extends Fragment {

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        setHasOptionsMenu(true);

        final PersianCalendarView persianCalendarView  = view.findViewById(R.id.persian_calendar);
        final TextView dateToday = view.findViewById(R.id.dateToday);
        final PersianCalendarHandler calendar = persianCalendarView.getCalendar();
        final PersianDate today = calendar.getToday();
        String dayAndMonth = calendar.getWeekDayName(today) +" " + calendar.formatNumber(today.getDayOfMonth())+
                " " +calendar.getMonthName(today) +" " + calendar.formatNumber(today.getYear());
        dateToday.setText(dayAndMonth);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_dashboard,menu);
        MenuItem menuItemEdit = menu.findItem(R.id.toolbar_action_edit);
        menuItemEdit.setVisible(false);
        MenuItem menuItemEdit2 = menu.findItem(R.id.menu_home_reset);
        menuItemEdit2.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
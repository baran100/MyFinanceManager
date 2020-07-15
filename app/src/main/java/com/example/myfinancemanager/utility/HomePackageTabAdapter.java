package com.example.myfinancemanager.utility;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myfinancemanager.R;
import com.example.myfinancemanager.activity.home.DashboardFragment;
import com.example.myfinancemanager.activity.home.ItemTransactionFragment;
import com.example.myfinancemanager.activity.home.ReportFragment;
import com.example.myfinancemanager.activity.home.TransactionFragment;
import com.google.android.material.tabs.TabLayout;


public class HomePackageTabAdapter extends FragmentStatePagerAdapter {
    private static Context mContext;
    TabLayout tabLayout;
    public HomePackageTabAdapter(FragmentManager fm, TabLayout _tabLayout, Context mContext) {
        super(fm);
        this.tabLayout = _tabLayout;
        HomePackageTabAdapter.mContext = mContext;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new DashboardFragment();
        }
        else if (position == 1)
        {
            fragment = new TransactionFragment();
        }
        else if (position == 2)
        {
            fragment = new ItemTransactionFragment();
        }
        else if (position == 3)
        {
            fragment = new ItemTransactionFragment();
        }
        else if (position == 4)
        {
            fragment = new ItemTransactionFragment();
        }
        else if (position == 5)
        {
            fragment = new ReportFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 6;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = mContext.getResources().getString(R.string.menu_title_dashboard);
        }
        else if (position == 1)
        {
            title = mContext.getResources().getString(R.string.home_tab_transactions);
        }
        else if (position == 2)
        {
            title = mContext.getResources().getString(R.string.home_tab_item);
        }
        else if (position == 3)
        {
            title = mContext.getResources().getString(R.string.menu_title_tags);
        }
        else if (position == 4)
        {
            title = mContext.getResources().getString(R.string.menu_title_person);
        }
        else if (position == 5)
        {
            title = mContext.getResources().getString(R.string.home_tab_report);
        }
        return title;
    }
}

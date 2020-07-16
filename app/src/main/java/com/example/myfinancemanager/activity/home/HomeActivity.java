package com.example.myfinancemanager.activity.home;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;
import com.example.myfinancemanager.BaseActivity;
import com.example.myfinancemanager.R;
import com.example.myfinancemanager.activity.add.AddEditTransactionActivity;
import com.example.myfinancemanager.activity.viewModel.BaseViewModel;
import com.example.myfinancemanager.activity.viewModel.HomeViewModel;
import com.example.myfinancemanager.utility.HomePackageTabAdapter;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends BaseActivity {

    private HomeViewModel viewModel;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    private HomePackageTabAdapter adapter;

    @Override
    protected Class<? extends BaseViewModel> getViewModelClass() {
        return HomeViewModel.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_home);
        viewModel = (HomeViewModel) super.viewModel;

        viewPager =  findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(2);
        tabLayout =  findViewById(R.id.home_tab_type);
        createTabFragment();
        changeTabsFont();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        addFab(R.layout.fab_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddEditTransactionActivity.class);
                startActivity(intent);

            }
        });

    }

    private void createTabFragment(){
        adapter = new HomePackageTabAdapter(getSupportFragmentManager(), tabLayout,this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void changeTabsFont() {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    AssetManager mgr = this.getAssets();
                    Typeface tf = Typeface.createFromAsset(mgr, "fonts/EstedadFDThin.ttf");//Font file in /assets
                    ((TextView) tabViewChild).setTypeface(tf);
                }
            }
        }
    }
}
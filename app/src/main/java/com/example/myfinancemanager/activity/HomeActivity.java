package com.example.myfinancemanager.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;
import com.example.myfinancemanager.BaseActivity;
import com.example.myfinancemanager.R;
import com.example.myfinancemanager.activity.account.AccountsActivity;
import com.example.myfinancemanager.activity.add.AddEditTransactionActivity;
import com.example.myfinancemanager.activity.category.CategoriesActivity;
import com.example.myfinancemanager.activity.person.PersonsActivity;
import com.example.myfinancemanager.activity.tag.TagsActivity;
import com.example.myfinancemanager.activity.viewModel.BaseViewModel;
import com.example.myfinancemanager.activity.viewModel.HomeViewModel;
import com.example.myfinancemanager.utility.HomePackageTabAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.idescout.sql.SqlScoutServer;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;

    private FloatingActionButton fab_main, fab1_transaction, fab2_account, fab3_category, fab4_tag, fab5_person;
    private Animation fab_open, fab_close, fab_clock, fab_antiClock;
    TextView txFabMenuTransaction, txFabMenuAccount,txFabMenuCategory, txFabMenuTag;
    Boolean isOpen = false;

    @Override
    protected Class<? extends BaseViewModel> getViewModelClass() {
        return HomeViewModel.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_home);
        HomeViewModel viewModel = (HomeViewModel) super.viewModel;

        initFab();
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

        fab_main.setOnClickListener(this);
        fab1_transaction.setOnClickListener(this);
        fab2_account.setOnClickListener(this);
        fab3_category.setOnClickListener(this);
        fab4_tag.setOnClickListener(this);
        fab5_person.setOnClickListener(this);


    }

    private void initFab(){
        fab_main = findViewById(R.id.fab_main);
        fab1_transaction = findViewById(R.id.fab1);
        fab2_account = findViewById(R.id.fab2);
        fab3_category = findViewById(R.id.fab3);
        fab4_tag = findViewById(R.id.fab4);
        fab5_person = findViewById(R.id.fab5);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_antiClock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);

        txFabMenuTransaction =  findViewById(R.id.txFabMenuTransaction);
        txFabMenuAccount =  findViewById(R.id.txFabMenuAccount);
        txFabMenuCategory =  findViewById(R.id.txFabMenuCategory);
        txFabMenuTag =  findViewById(R.id.txFabMenuTag);

    }
    private void createTabFragment(){
        HomePackageTabAdapter adapter = new HomePackageTabAdapter(getSupportFragmentManager(), tabLayout, this);
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

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.fab_main:
                if (isOpen) {
                txFabMenuTransaction.setVisibility(View.INVISIBLE);
                txFabMenuAccount.setVisibility(View.INVISIBLE);
                txFabMenuCategory.setVisibility(View.INVISIBLE);
                txFabMenuTag.setVisibility(View.INVISIBLE);
                fab3_category.startAnimation(fab_close);
                fab4_tag.startAnimation(fab_close);
                fab5_person.startAnimation(fab_close);
                fab2_account.startAnimation(fab_close);
                fab1_transaction.startAnimation(fab_close);
                fab_main.startAnimation(fab_antiClock);
                fab2_account.setClickable(false);
                fab1_transaction.setClickable(false);
                fab3_category.setClickable(false);
                fab4_tag.setClickable(false);
                fab5_person.setClickable(false);
                isOpen = false;
            } else {
                txFabMenuTransaction.setVisibility(View.VISIBLE);
                txFabMenuAccount.setVisibility(View.VISIBLE);
                txFabMenuCategory.setVisibility(View.VISIBLE);
                txFabMenuTag.setVisibility(View.GONE);
                fab3_category.startAnimation(fab_open);
                fab4_tag.startAnimation(fab_open);
                fab5_person.startAnimation(fab_open);
                fab2_account.startAnimation(fab_open);
                fab1_transaction.startAnimation(fab_open);
                fab_main.startAnimation(fab_clock);
                fab2_account.setClickable(true);
                fab1_transaction.setClickable(true);
                fab3_category.setClickable(true);
                fab4_tag.setClickable(true);
                fab5_person.setClickable(true);
                isOpen = true;
            }
                break;

            case R.id.fab1:
                intent = new Intent(HomeActivity.this, AddEditTransactionActivity.class);
                startActivity(intent);
            break;

            case R.id.fab2:
                intent = new Intent(HomeActivity.this, AccountsActivity.class);
                startActivity(intent);
                break;

            case R.id.fab3:
                intent = new Intent(HomeActivity.this, CategoriesActivity.class);
                startActivity(intent);
                break;

            case R.id.fab4:
                intent = new Intent(HomeActivity.this, TagsActivity.class);
                startActivity(intent);
                break;

            case R.id.fab5:
                intent = new Intent(HomeActivity.this, PersonsActivity.class);
                startActivity(intent);
                break;
            default:
                throw new UnsupportedOperationException(getString(R.string.trying_to_call_unknown) + v.getId());
        }

    }
}
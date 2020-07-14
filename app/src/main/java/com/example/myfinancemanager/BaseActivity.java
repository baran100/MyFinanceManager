package com.example.myfinancemanager;


import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.TaskStackBuilder;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myfinancemanager.activity.home.HomeActivity;
import com.example.myfinancemanager.databinding.ActivityBaseBinding;
import com.example.myfinancemanager.databinding.ActivityStackedBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.example.myfinancemanager.activity.viewModel.BaseViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {
    // delay to launch nav drawer item, to allow close animation to play
    public static final int NAVDRAWER_LAUNCH_DELAY = 250;
    // fade in and fade out durations for the main content when switching between
    // different Activities of the app through the Nav Drawer
    public static final int MAIN_CONTENT_FADEOUT_DURATION = 150;
    public static final int MAIN_CONTENT_FADEIN_DURATION = 250;

    // Navigation drawer:
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private CoordinatorLayout contentWrapper;
    private View content;
    private LayoutInflater inflater;

    // Helper
    private Handler mHandler;
    protected SharedPreferences mSharedPreferences;
    protected BaseViewModel viewModel;

    private static Handler periodicHandler;

    private List<MenuItem.OnMenuItemClickListener> editMenuClickListeners = new ArrayList<MenuItem.OnMenuItemClickListener>();


    protected abstract Class<? extends BaseViewModel> getViewModelClass();

    protected BaseViewModel getViewModel() {
        return ViewModelProviders.of(this).get(getViewModelClass());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler();
        overridePendingTransition(0, 0);
        viewModel = getViewModel();

        if (viewModel.showDrawer()) {
            ActivityBaseBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_base);
            mNavigationView = binding.getRoot().findViewById(R.id.nav_view);
            mNavigationView.setNavigationItemSelectedListener(this);
            binding.setViewModel(viewModel);
        } else {
            ActivityStackedBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_stacked);
            binding.setViewModel(viewModel);
        }

        viewModel.getTitle().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String title) {
                BaseActivity.this.setTitle(title);
            }
        });

        viewModel.getTitleId().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer titleId) {
                viewModel.setTitle(getString(titleId));
            }
        });
        viewModel.getNavigationDrawerId().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer navigationDrawerId) {
                if (navigationDrawerId == null || navigationDrawerId == -1) {
                    selectNavigationItem(-1);
                } else {
                    selectNavigationItem(navigationDrawerId);
                }
            }
        });
        viewModel.getNavigationDrawerId().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer navigationDrawerId) {
                if (navigationDrawerId == null ||navigationDrawerId == -1){
                    selectNavigationItem(-1);
                }else {
                    selectNavigationItem(navigationDrawerId);
                }
            }
        });

        contentWrapper = findViewById(R.id.content_wrapper);
        inflater = LayoutInflater.from(contentWrapper.getContext());
    }

    protected final View setContent(@LayoutRes int layout) {
        if (content != null) {
            contentWrapper.removeView(content);
        }
        content = inflater.inflate(layout, contentWrapper, false);
        contentWrapper.addView(content);
        return content;
    }

    public final FloatingActionButton addFab(@LayoutRes int layout, View.OnClickListener listener) {
        FloatingActionButton fab = (FloatingActionButton) inflater.inflate(layout, contentWrapper, false);
        contentWrapper.addView(fab);
        if (listener != null) {
            fab.setOnClickListener(listener);
        }
        return fab;
    }

    protected final FloatingActionButton addFab(@LayoutRes int layout) {
        return addFab(layout, null);
    }

    @Override
    public void onBackPressed() {
        System.out.println("back pressed");
        if (viewModel.showDrawer()) {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        final int itemId = item.getItemId();

        return goToNavigationItem(itemId);
    }

    protected boolean goToNavigationItem(final int itemId) {
        if (itemId == viewModel.getNavigationDrawerId().getValue()) {
            // just close drawer because we are already in this activity
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }

        // delay transition so the drawer can close
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callDrawerItem(itemId);
            }
        }, NAVDRAWER_LAUNCH_DELAY);

        mDrawerLayout.closeDrawer(GravityCompat.START);

        selectNavigationItem(itemId);

        // fade out the active activity
        if (contentWrapper != null) {
            contentWrapper.animate().alpha(0).setDuration(MAIN_CONTENT_FADEOUT_DURATION);
        }
        return true;
    }

    // set active navigation item
    private void selectNavigationItem(int itemId) {
        if (viewModel.showDrawer()) {
            for (int i = 0; i < mNavigationView.getMenu().size(); i++) {
                boolean b = itemId == mNavigationView.getMenu().getItem(i).getItemId();
                mNavigationView.getMenu().getItem(i).setChecked(b);
            }
        }
    }

    /**
     * Enables back navigation for activities that are launched from the NavBar. See
     * {@code AndroidManifest.xml} to find out the parent activity names for each activity.
     *
     * @param intent
     */
    private void createBackStack(Intent intent) {
        System.out.println("Added to backtrack: " + intent.getComponent().getClassName());
        FragmentManager fm = getFragmentManager();
        System.out.println("Backtrack size: " + fm.getBackStackEntryCount());
        for (int i = 0; i < fm.getBackStackEntryCount(); i++){
            System.out.println("number " + i + ": " + fm.getBackStackEntryAt(i).getId());
        }



        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        TaskStackBuilder builder = TaskStackBuilder.create(this);
        builder.addNextIntentWithParentStack(intent);
        builder.startActivities();
    }

    /**
     * This method manages the behaviour of the navigation drawer
     * Add your menu items (ids) to res/menu/activity_main_drawer.xml
     *
     * @param itemId Item that has been clicked by the user
     */
    private void callDrawerItem(final int itemId) {
        Intent intent;

        switch (itemId) {
            case R.id.nav_main:
                intent = new Intent(this, HomeActivity.class);
                createBackStack(intent);
                break;
           default:
                throw new UnsupportedOperationException("Trying to call unknown drawer item! Id: " + itemId);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
        }
        if (viewModel.showDrawer()) {

            mDrawerLayout = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, mDrawerLayout, toolbar, R.string.nav_drawer_toggle_open_desc, R.string.nav_drawer_toggle_close_desc);
            mDrawerLayout.addDrawerListener(toggle);
            toggle.syncState();
        }

        if (contentWrapper != null) {
            contentWrapper.setAlpha(0);
            contentWrapper.animate().alpha(1).setDuration(MAIN_CONTENT_FADEIN_DURATION);
        }
    }
}
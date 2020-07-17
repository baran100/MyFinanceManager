package com.example.myfinancemanager;


import android.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.databinding.DataBindingUtil;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.LayoutRes;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.myfinancemanager.activity.AboutActivity;
import com.example.myfinancemanager.activity.HelpActivity;
import com.example.myfinancemanager.activity.account.AccountsActivity;
import com.example.myfinancemanager.activity.category.CategoriesActivity;
import com.example.myfinancemanager.activity.HomeActivity;
import com.example.myfinancemanager.activity.person.PersonsActivity;
import com.example.myfinancemanager.activity.statistic.StatisticsActivity;
import com.example.myfinancemanager.activity.tag.TagsActivity;
import com.example.myfinancemanager.activity.viewModel.BaseViewModel;
import com.example.myfinancemanager.databinding.ActivityBaseBinding;
import com.example.myfinancemanager.databinding.ActivityStackedBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import androidx.core.app.TaskStackBuilder;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christopher Beckmann,
 * @author Karola Marky
 * @author Felix Hofmann
 * @author Leonard Otto
 *
 * @version 20181129
 * <p>
 * This class is a parent class of all activities.
 * just inject your activities content via the setContent() method.
 */
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


    private void schedulePeriodicTask() {
        if (periodicHandler == null) {
            periodicHandler = new Handler();
        } else {
            return;
        }
        final Runnable periodicRunner = new Runnable() {
            @Override
            public void run() {
                //PeriodicDatabaseWorker.work();
                //periodicHandler.postDelayed(this, PeriodicDatabaseWorker.DURATION_BETWEEN_WORK);
            }
        };
        periodicHandler.post(periodicRunner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        schedulePeriodicTask();

        //mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mHandler = new Handler();
        overridePendingTransition(0, 0);

        viewModel = getViewModel();
        ;
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

        contentWrapper = findViewById(R.id.content_wrapper);
        inflater = LayoutInflater.from(contentWrapper.getContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_pencil, menu);
        MenuItem menuItemEdit = menu.findItem(R.id.toolbar_action_edit);
        menuItemEdit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                menuItemEditClicked(item);
                return true;
            }
        });

        return viewModel.doShowEditMenu();
    }

    private void menuItemEditClicked(MenuItem item) {
        for (int i = 0; i < editMenuClickListeners.size(); i++) {
            editMenuClickListeners.get(i).onMenuItemClick(item);
        }
    }

    protected void addEditMenuClickListener(MenuItem.OnMenuItemClickListener listener) {
        editMenuClickListeners.add(listener);
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
            case R.id.nav_categories:
                intent = new Intent(this, CategoriesActivity.class);
                createBackStack(intent);
                break;
            case R.id.nav_accounts:
                intent = new Intent(this, AccountsActivity.class);
                createBackStack(intent);
                break;
            case R.id.nav_tags:
                intent = new Intent(this, TagsActivity.class);
                createBackStack(intent);
                break;
            case R.id.nav_persons:
                intent = new Intent(this, PersonsActivity.class);
                createBackStack(intent);
                break;
            case R.id.nav_tutorial:
                intent = new Intent(this, AboutActivity.class);
                createBackStack(intent);
                break;
            case R.id.nav_about:
                intent = new Intent(this, AboutActivity.class);
                createBackStack(intent);
                break;
            case R.id.nav_help:
                intent = new Intent(this, HelpActivity.class);
                createBackStack(intent);
                break;

            case R.id.nav_repeating_transactions:
                intent = new Intent(this, AboutActivity.class);
                createBackStack(intent);
                break;
            case R.id.nav_statistics:
                intent = new Intent(this, StatisticsActivity.class);
                createBackStack(intent);
                break;
            default:
                throw new UnsupportedOperationException(getString(R.string.trying_to_call_unknown) + itemId);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);

            View view = toolbar.getChildAt(0);
            if (view != null && view instanceof TextView) {
                TextView title = (TextView) view;
                AssetManager mgr = getAssets();
                Typeface tf = Typeface.createFromAsset(mgr, "fonts/EstedadFDMedium.ttf");//Font file in /assets
                title.setTypeface(tf);
                //toolbar.getViewTreeObserver().removeOnGlobalLayoutListener( this);
            }
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

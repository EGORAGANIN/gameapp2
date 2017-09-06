package ru.egoraganin.testapp.UI.Activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import ru.egoraganin.testapp.R;
import ru.egoraganin.testapp.UI.Activity.Model.BaseViewGroup;
import ru.egoraganin.testapp.UI.View.ActionView.SearchView;
import ru.egoraganin.testapp.Utils.Display.ScreenUtils;

import java.lang.reflect.Field;


public abstract class BaseActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        SearchView.OnQueryTextListener,
        AppBarLayout.OnOffsetChangedListener,
        BottomNavigationView.OnNavigationItemSelectedListener {
    protected BaseViewGroup baseViewGroup;
    protected DrawerLayout drawerLayout;
    protected SearchView searchView;
    protected NavigationView navigationView;
    protected Toolbar toolbar;
    protected AppBarLayout appBarLayout;
    protected BottomNavigationView bottomNavigationView;

    private FragmentManager fragmentManager;
    private ActionBarDrawerToggle drawerToggle;
    private boolean isSingleCallInit;
    private boolean isLollipop;
    private boolean isInitialized;
    private int containerResource;
    private int resourceSearchView;
    private int resourceMenu;
    private int resourceIconSearch;
    private int resourceCloseSearch;
    private int resourceUnderLineSearchView;
    private int resourceColorSearchView;
    private int resourceAsIndicator;
    private boolean appBarLayoutIsClose;
    private Fragment currentFragment;

    protected abstract void init(BaseViewGroup baseViewGroup);

    @Override
    public void setContentView(View view) {
        baseViewGroup = (BaseViewGroup) view;

        super.setContentView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (resourceMenu != -1) {
            MenuInflater inflater = new MenuInflater(this);
            inflater.inflate(resourceMenu, menu);
        }

        if (resourceSearchView != -1) {
            MenuItem menuItem = menu.findItem(resourceSearchView);
            searchView = (SearchView) menuItem.getActionView();

            if (searchView == null)
                searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

            searchView.setOnQueryTextListener(this);

            if (resourceIconSearch != -1) {
                searchView.setIconResource(resourceIconSearch);
                searchView.setIconCloseSearchView(resourceIconSearch);
                searchView.setHint("");

                if (resourceColorSearchView != -1) {
                    searchView.setTextColor(resourceColorSearchView);
                }
            }

            if (resourceCloseSearch != -1) {
                searchView.setIconCloseSearchView(resourceCloseSearch);
            }

            if (resourceUnderLineSearchView != -1) {
                searchView.setUnderlineBackground(resourceUnderLineSearchView);
            }
        }

        onMenuInitialized(menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void initDrawableAsIndicator(@DrawableRes int resource) {
        final Drawable drawable = getResources().getDrawable(resource);
        getSupportActionBar().setHomeAsUpIndicator(drawable);

        resourceAsIndicator = resource;
    }

    private void insideInit() {
        isLollipop = false;
        resourceMenu = -1;
        resourceSearchView = -1;
        resourceIconSearch = -1;
        resourceCloseSearch = -1;
        resourceCloseSearch = -1;

        appBarLayoutIsClose = false;

        Log.d(BaseActivity.class.getSimpleName(), "Display type: " + ScreenUtils.getDeviceResolution(this));

        init(baseViewGroup);

        isLollipop = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;

        if (isLollipop) initLollipop();
    }

    protected void setColorHomeAsIndicator(@ColorRes int color) {
        final Drawable upArrow = getResources().getDrawable(resourceAsIndicator);
        upArrow.setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    protected void initLollipop() {

    }

    protected void onMenuInitialized(Menu menu) {

    }

    public void initSearchView(@IdRes int resource) {
        resourceSearchView = resource;
    }

    protected void initToolbar(@IdRes int resource) {
        toolbar = (Toolbar) findViewById(resource);
        setSupportActionBar(toolbar);
    }

    protected void initAppbarLayout(@IdRes int resource) {
        appBarLayout = (AppBarLayout) findViewById(resource);
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!isInitialized || !isSingleCallInit) {
            isSingleCallInit = false;

            insideInit();

            isInitialized = true;
        }
    }

    protected void initFragmentManager(@IdRes int containerResource) {
        this.containerResource = containerResource;
        fragmentManager = getSupportFragmentManager();
    }

    public void setFragment(Fragment fragment) {
        currentFragment = fragment;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerResource, fragment);
        fragmentTransaction.commit();
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    protected void setSingleCallInit(boolean singleCallInit) {
        this.isSingleCallInit = singleCallInit;
    }

    protected void initBottomNavigationMenu(@IdRes int resource, boolean disableShiftingMode) {
        bottomNavigationView = (BottomNavigationView) findViewById(resource);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        onBottomNavigationMenuInitialized(bottomNavigationView.getMenu());

        if (!disableShiftingMode) return;

        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);

        try {
            Field shiftingMode = bottomNavigationMenuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(bottomNavigationMenuView, false);
            shiftingMode.setAccessible(false);

            for (int i = 0; i < bottomNavigationMenuView.getChildCount(); i++) {
                BottomNavigationItemView bottomNavigationItemView = (BottomNavigationItemView) bottomNavigationMenuView.getChildAt(i);
                bottomNavigationItemView.setShiftingMode(false);
                bottomNavigationItemView.setChecked(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onBottomNavigationMenuInitialized(Menu menu) {

    }

    protected boolean isSingleCallInit() {
        return isSingleCallInit;
    }

    protected void initDrawerLayout(@IdRes int resource) {
        drawerLayout = (DrawerLayout) findViewById(resource);
    }

    protected void initTextColorSearchView(@ColorRes int resource) {
        this.resourceColorSearchView = resource;
    }

    protected void initIconSearchView(@DrawableRes int resource) {
        this.resourceIconSearch = resource;
    }

    protected void initIconCloseSearchView(@DrawableRes int resource) {
        this.resourceCloseSearch = resource;
    }

    protected void initUnderlineSearchView(@DrawableRes int resource) {
        this.resourceUnderLineSearchView = resource;
    }

    protected void initStrips(@StringRes int open, @StringRes int close) {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar == null) return;

        actionBar.setDisplayHomeAsUpEnabled(true);

        if (drawerLayout == null) return;

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, open, close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    protected void initBackArrow() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);

            clearCheckedMenu();
        }

        item.setChecked(true);

        return true;
    }

    private void clearCheckedMenu() {
        Menu menu = navigationView.getMenu();

        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setChecked(false);
        }
    }

    protected void initNavigationView(@IdRes int resource) {
        navigationView = (NavigationView) findViewById(resource);

        navigationView.setNavigationItemSelectedListener(this);

        onNavigationMenuInitialized(navigationView.getHeaderView(0), navigationView.getMenu());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle != null && drawerToggle.onOptionsItemSelected(item)) return true;
        if (item.getItemId() == android.R.id.home) onBackPressed();

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }

        if (searchView != null && !searchView.isIconified()) {
            searchView.onActionViewCollapsed();
            return;
        }

        super.onBackPressed();
    }

    protected void initMenu(@MenuRes int resource) {
        this.resourceMenu = resource;
    }

    protected void onNavigationMenuInitialized(View header, Menu menu) {

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (searchView != null) searchView.onActionViewCollapsed();

        return false;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        appBarLayoutIsClose = verticalOffset != 0;
    }

    protected boolean checkPermission(String[] permissions) {
        for (int i = 0; i < permissions.length; i++) {
            if (ActivityCompat.checkSelfPermission(this, permissions[i]) != PermissionChecker.PERMISSION_GRANTED)
                return false;
        }

        return true;
    }

    protected void requestPermissions(String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, 537);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode != 537) return;

        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PermissionChecker.PERMISSION_GRANTED) return;
        }

        onRequestPermissionGranted(permissions);
    }

    protected boolean checkAndRequestPermission(String[] permission) {
        if (checkPermission(permission)) return true;

        requestPermissions(permission);

        return false;
    }

    protected void onRequestPermissionGranted(String[] permissions) {

    }

    protected void startActivity(Class name) {
        startActivity(new Intent(this, name));
    }

    protected boolean appBarLayoutIsClose() {
        return appBarLayoutIsClose;
    }

    protected boolean isLollipop() {
        return isLollipop;
    }

    public static final String DATA_INTENT_ACTIVITY = "DATA_INTENT_ACTIVITY";
}
package com.myriadmobile.Challenge2015;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ChildManager {
    SharedPreferences prefs;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.showOverflowMenu();
        toolbar.setVisibility(View.GONE);
        toolbar.setOnMenuItemClickListener(new MenuItemOnClickListener());
        if (savedInstanceState != null)
            return;
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (prefs.contains("login")) {
            getSupportActionBar().setTitle(prefs.getString("login", "Default Email"));
            KingdomsFragment kingdomsFragment = new KingdomsFragment();
            toolbar.setVisibility(View.VISIBLE);
            getFragmentManager().beginTransaction().add(R.id.fragment_container, kingdomsFragment).commit();
        } else {
            LoginFragment loginFragment = new LoginFragment();
            getFragmentManager().beginTransaction().add(R.id.fragment_container, loginFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }
    public Context getContext() {
        return this;
    }
    public class MenuItemOnClickListener implements Toolbar.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            prefs.edit().remove("login").commit();
            LoginFragment loginFragment = new LoginFragment();
            if(getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStackImmediate();
            }
            swapFragments(R.id.fragment_container, loginFragment, false);
            return false;
        }
    }
    //Everything below here is implementation for the Child Manager interface.
    public void setPreferences (String key, String email) {
        prefs.edit().putString(key, email).commit();
        getSupportActionBar().setTitle(email);
    }
    public void swapFragments (int container, Fragment fragment, boolean backstack) {
        FragmentTransaction fTrans = getFragmentManager().beginTransaction().replace(container, fragment);
        if (backstack)
            fTrans.addToBackStack("kingdoms");
        fTrans.commit();
        if (!(fragment instanceof LoginFragment)) {
            toolbar.setVisibility(View.VISIBLE);
        }
        else {
            toolbar.setVisibility(View.GONE);
        }
    }
    public void startNewActivity (Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        this.startActivity(intent);
    }
}


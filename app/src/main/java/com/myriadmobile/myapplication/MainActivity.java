package com.myriadmobile.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity implements FragmentSwapper {
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_main);
        if (savedInstanceState != null)
            return;
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (prefs.contains("login") && prefs.getBoolean("login", true)) {
            KingdomsFragment kingdomsFragment = new KingdomsFragment();
            getFragmentManager().beginTransaction().add(R.id.main_container, kingdomsFragment).commit();
        } else {
            LoginFragment loginFragment = new LoginFragment();
            getFragmentManager().beginTransaction().add(R.id.main_container, loginFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
    public void setPreferences (String key, Boolean bool) {
        prefs.edit().putBoolean(key, bool);
    }
    public void swapFragments (int container, Fragment fragment) {
        getFragmentManager().beginTransaction().replace(container, fragment);
    }
    public Context getContext() {
        return this;
    }
}

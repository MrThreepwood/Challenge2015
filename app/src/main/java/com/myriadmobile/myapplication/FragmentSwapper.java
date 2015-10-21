package com.myriadmobile.myapplication;

import android.content.Context;

/**
 * Created by joshuaswoyer on 10/21/15.
 */
public interface FragmentSwapper {
    public void swapFragments(int container, android.app.Fragment fragment);
    public void setPreferences(String key, Boolean bool);
    public Context getContext();
}

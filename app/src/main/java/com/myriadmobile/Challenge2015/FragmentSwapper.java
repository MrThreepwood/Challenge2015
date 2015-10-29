package com.myriadmobile.Challenge2015;

import android.content.Context;

/**
 * Created by joshuaswoyer on 10/21/15.
 */
public interface FragmentSwapper {
    public void swapFragments(int container, android.app.Fragment fragment, boolean bool);
    public void setPreferences(String key, String string);
    public Context getContext();
}
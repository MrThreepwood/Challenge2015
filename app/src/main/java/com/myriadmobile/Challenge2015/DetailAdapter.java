package com.myriadmobile.Challenge2015;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by joshuaswoyer on 10/23/15.
 */
public class DetailAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public DetailAdapter (FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    @Override
    public int getCount() {
        if(fragments == null)
            return 0;
        return fragments.size();
    }
}

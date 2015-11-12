package com.myriadmobile.Challenge2015;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by joshuaswoyer on 10/23/15.
 */
public class KingdomDetailsPager extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public KingdomDetailsPager(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    @Override
    public int getCount() {
        if(fragments == null) {
            return 0;
        }
        return fragments.size();
    }
}

package com.shopspreeng.nearby;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shopspreeng.nearby.ads.AdsFragment;
import com.shopspreeng.nearby.hotspot.HotspotFragment;

/**
 * Created by Thadeus-APMIS on 8/26/2018.
 */

public class DashboardFragmentPagerAdapter extends FragmentPagerAdapter {

    public DashboardFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new HotspotFragment();
            default:
                return new AdsFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 1:
                return "HOTSPOTS";
            default:
                return "ADS";
        }
    }
}

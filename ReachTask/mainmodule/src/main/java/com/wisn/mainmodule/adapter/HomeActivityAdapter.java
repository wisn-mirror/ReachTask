package com.wisn.mainmodule.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wisn.mainmodule.view.fragment.HomeFragmentFactory;

import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/23 20:40
 */


public class HomeActivityAdapter extends FragmentPagerAdapter {
    List<String> data=null;
    public HomeActivityAdapter(FragmentManager fm) {
        super(fm);
        data= HomeFragmentFactory.getFragmentTag();
    }

    @Override
    public Fragment getItem(int position) {
        return HomeFragmentFactory.getFragment(data.get(position));
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        return super.getPageTitle(position);
        return data.get(position);
    }

}

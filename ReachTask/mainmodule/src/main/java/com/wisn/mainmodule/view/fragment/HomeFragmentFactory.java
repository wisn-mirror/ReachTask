package com.wisn.mainmodule.view.fragment;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/23 20:45
 */


public class HomeFragmentFactory {
    public static HashMap<String, Fragment> mFragments = null;
    private static List<String> data;

    public static List<String> getFragmentTag() {
        data = new ArrayList<>();
        data.add("Message");
        data.add("Contact");
        data.add("Work");
        data.add("Mine");
        return data;
    }

    public static Fragment getFragment(String fag) {
        if (mFragments == null) {
            synchronized (HomeFragmentFactory.class) {
                mFragments = new HashMap<>();
            }
        }
        Fragment fragment = mFragments.get(fag);
        if (fragment == null) {
            fragment = createFragment(fag);
            if (fragment != null)
                mFragments.put(fag, fragment);
        }
        return fragment;
    }

    private static Fragment createFragment(String fag) {
        Fragment fragment = null;
        switch (fag) {
            case "Message":
                fragment = new MessageFragament();
                break;
            case "Contact":
                fragment = new ContactFragament();
                break;
            case "Work":
                fragment = new WorkFragament();
                break;
            case "Mine":
                fragment = new MineFragament();
                break;

        }
        return fragment;
    }

}

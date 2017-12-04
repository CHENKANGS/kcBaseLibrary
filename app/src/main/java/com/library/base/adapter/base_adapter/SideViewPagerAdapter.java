package com.library.base.adapter.base_adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @Class
 * @Description (实现的主要功能)
 * @Date Administratoron 2016/1/26 10:58
 * @Upate 修改者:;修改日期:;修改内容:.
 */
public class SideViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArray;
    private String[] title_arr = null;

    public SideViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArray, String[] title_arr) {
        super(fm);
        this.title_arr = title_arr;
        this.fragmentArray = fragmentArray;
    }
    public SideViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return this.fragmentArray.get(position);
    }

    @Override
    public int getCount() {
        return this.fragmentArray.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title_arr[position];
    }
}

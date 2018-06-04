package com.example.huadongchongtu;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private FragmentManager mFm;
    private Context mContext;

    public MyViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mFm = fm;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new Fragment1();
            case 1:
                return new Fragment2();
            case 2:
                return new Fragment3();
            default:
                break;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}

package com.tistory.everysw.wherewego;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;

public class ActionBarTabListener<T extends Fragment> implements TabListener {

    private Activity mActivity;
    private Class<T> mClass;
    private Fragment mFragment;
    
    public ActionBarTabListener(Activity activity, Class<T> clz) {
        mActivity = activity;
        mClass = clz;
    }
    

    @Override
    public void onTabSelected(Tab arg0, FragmentTransaction ft) {
        
        if(mFragment == null) {
            mFragment = Fragment.instantiate(mActivity, mClass.getName());
            ft.add(android.R.id.content, mFragment);
        } else {
            ft.attach(mFragment);
        }
        
    }
    
    @Override
    public void onTabUnselected(Tab arg0, FragmentTransaction ft) {
        if(mFragment != null) {
            ft.detach(mFragment);
        }      
    }
    
    @Override
    public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub
        
    }
    

}

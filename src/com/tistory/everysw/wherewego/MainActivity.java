package com.tistory.everysw.wherewego;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.LinearLayout;

public class MainActivity extends ActionBarActivity  {

    private LinearLayout mainLayout;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mainLayout = (LinearLayout)findViewById(R.id.mainLayout);
        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        bar.addTab(bar.newTab().setText(R.string.tab_my_history)
                .setTabListener(new ActionBarTabListener<MyHistoryFragment>(this, MyHistoryFragment.class)));
        bar.addTab(bar.newTab().setText(R.string.tab_add_place)
                .setTabListener(new ActionBarTabListener<AddPlaceFragment>(this, AddPlaceFragment.class)));
        bar.addTab(bar.newTab().setText(R.string.tab_other_places)
                .setTabListener(new ActionBarTabListener<OtherPlacesFragment>(this, OtherPlacesFragment.class)));            
        
    }


}

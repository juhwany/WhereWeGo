package com.tistory.everysw.wherewego;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;


public class AddPlaceFragment extends Fragment {

    private SupportMapFragment fragment;
    private GoogleMap map;    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.place_map, container, false);
        
        return view;
    }

    // Nested Fragment doesn't support. So creates Google Map fragment, and add it to fragment dynamically.
    // http://stackoverflow.com/questions/14565460/error-opening-supportmapfragment-for-second-time
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState);
        
        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, fragment).commit();
        }
        
    }
  
}

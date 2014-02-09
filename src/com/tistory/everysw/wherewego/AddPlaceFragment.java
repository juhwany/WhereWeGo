package com.tistory.everysw.wherewego;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;


public class AddPlaceFragment extends Fragment implements OnClickListener {

    private SupportMapFragment mNestedFragment;
    private GoogleMap mMap;
    private MapHandler mMapHandler;
    private Button mMyLocationBtn;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.place_map, container, false);
        
        return view;
    }
    
    @Override
    public void onResume() {
        
        super.onResume();
        if (mMap == null) {
            mMap = mNestedFragment.getMap();
            mMapHandler = new MapHandler(getActivity().getApplicationContext(), mMap);
        }

        mMyLocationBtn = (Button)getView().findViewById(R.id.myLocationBtn);
        mMyLocationBtn.setOnClickListener(this);        
        
    }    

    // Nested Fragment doesn't support. So creates Google Map fragment, and add it to fragment dynamically.
    // http://stackoverflow.com/questions/14565460/error-opening-supportmapfragment-for-second-time
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState);
        
        FragmentManager fm = getChildFragmentManager();
        mNestedFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (mNestedFragment == null) {
            mNestedFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, mNestedFragment).commit();
        }
        
    }

    @Override
    public void onClick(View v) {
        
        if(v.getId() == R.id.myLocationBtn) {
            mMapHandler.updateCamera();
        }
        
    }
  
    
    
}

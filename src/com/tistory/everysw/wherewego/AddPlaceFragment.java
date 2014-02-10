package com.tistory.everysw.wherewego;

import android.app.Dialog;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;



public class AddPlaceFragment extends Fragment 
                                implements OnClickListener,
                                GooglePlayServicesClient.ConnectionCallbacks,
                                GooglePlayServicesClient.OnConnectionFailedListener
                                {

    private SupportMapFragment mNestedFragment;
    private GoogleMap mMap;
    private MapHandler mMapHandler;
    private Button mMyLocationBtn;
    private Button mAddPlaceBtn;
    
    private LocationClient locationClient;
    
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
        
        mAddPlaceBtn = (Button)getView().findViewById(R.id.addPlaceBtn);
        mAddPlaceBtn.setOnClickListener(this);
        
        startLocationUpdate();
        
    }    

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdate();
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
        } else if(v.getId() == R.id.addPlaceBtn) {
            showAddPlaceWindow();
        }        
    }
    
    private void showAddPlaceWindow() {
        
        if(getView().findViewById(R.id.new_place_info_layout) == null) {
            ViewGroup insertPoint = (ViewGroup)(getView().findViewById(R.id.add_place_info_insert));
            View v = new AddPlaceInfoView(getActivity());
            insertPoint.addView(v);            
        }
    }
    
    void startLocationUpdate() {
        if(isGoogleServiceAvailable()) {
            setUpLocationClientIfNeeded();
            locationClient.connect();
        }
    }
    
    void stopLocationUpdate() {
        if(isGoogleServiceAvailable()) {        
            if(locationClient.isConnected()) {
                locationClient.removeLocationUpdates(mMapHandler);
            }
            
            locationClient.disconnect();
        }
    }

    private void setUpLocationClientIfNeeded() {
        if (locationClient == null) {
            locationClient = new LocationClient(
                    getActivity().getApplicationContext(),
                    this,
                    this);
        }
    }
    
    @Override
    public void onConnected(Bundle arg0) {
        
        LocationRequest request = LocationRequest.create()
                .setInterval(5000)
                .setFastestInterval(16)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        
        locationClient.requestLocationUpdates(request, mMapHandler); 
        Location lastLocation = locationClient.getLastLocation();
        if(lastLocation != null) {
            LatLng location = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            mMapHandler.setCurrentLocation(location);           
        }
    }

    @Override
    public void onDisconnected() {
        // TODO Auto-generated method stub
        
    }    
  
    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub
        
    }
    
    private boolean isGoogleServiceAvailable() {

        // Check that Google Play services is available
        int resultCode =
                GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());

        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            return true;
        // Google Play services was not available for some reason
        } else {
            // Display an error dialog
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), 0);
            if (dialog != null) {
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(dialog);
                errorFragment.show(getFragmentManager(), getResources().getString(R.string.app_name));
            }
            
            return false;
        }
    }    
    
    
}

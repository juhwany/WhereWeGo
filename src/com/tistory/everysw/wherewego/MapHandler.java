package com.tistory.everysw.wherewego;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class MapHandler implements 
                        OnMapClickListener,
                        LocationListener
                        {


    public static final float DEFAULT_LOCATION_SEOUL_LATITUDE = 37.540705f;
    public static final float DEFAULT_LOCATION_SEOUL_LONGITUDE = 126.956764f;
    public static final float MAP_DEFAULT_LOCATION_ZOOM = 11f;
    public static final float MAP_CURRENT_LOCATION_ZOOM = 18f;
    public static final int MIN_POLYLINE_POINTS = 2;
    public static final int MIN_POLYGON_POINTS = 3;  

    private Context context;
    private GoogleMap googleMap;
    private LatLng currentLocation = new LatLng(DEFAULT_LOCATION_SEOUL_LATITUDE, DEFAULT_LOCATION_SEOUL_LONGITUDE);    
    
    private ArrayList<LatLng> currentMarkedPointsList = new ArrayList<LatLng>();
    private ArrayList<Marker> currentMakersList = new ArrayList<Marker>();    


    interface OnNewPlaceAddedOnMapListener {
        void onNewPlaceAddedOnMap();
    }    
    
    OnNewPlaceAddedOnMapListener newPlacedAddedListener;
    
    public MapHandler(Context ctx, GoogleMap map) {
        
        context = ctx;

        googleMap = map;
        googleMap.setOnMapClickListener(this);     

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, MAP_DEFAULT_LOCATION_ZOOM));
        
    }
    
    public void setOnNewPlaceAddedOnMapListener(OnNewPlaceAddedOnMapListener listener) {
        newPlacedAddedListener = listener;
    }
    
    public void setCurrentLocation(LatLng location) {
        currentLocation = location;
    }

    public void updateCamera() {

        if(currentLocation != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, MAP_CURRENT_LOCATION_ZOOM));
        }        
    }
    
    @Override
    public void onLocationChanged(Location location) {

        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        updateCamera();
    }    
    
    
    @Override
    public void onMapClick(LatLng arg0) {
        googleMap.clear();
        currentMakersList.clear();
        currentMarkedPointsList.clear();
        
        //placeInfoInputLayout.setVisibility(View.GONE);
    }

    
    private List<com.qualcommlabs.usercontext.protocol.Location> convertLatLngToLocation(List<LatLng> latlngList) {
        
        List<com.qualcommlabs.usercontext.protocol.Location> locationList = new ArrayList<com.qualcommlabs.usercontext.protocol.Location>();
        
        if(latlngList.size() > MIN_POLYGON_POINTS) {
            
            for(LatLng latlng : latlngList) {
                com.qualcommlabs.usercontext.protocol.Location qualCommLocation;
                qualCommLocation = new com.qualcommlabs.usercontext.protocol.Location();
                qualCommLocation.setLatitude(latlng.latitude);
                qualCommLocation.setLongitude(latlng.longitude);
                locationList.add(qualCommLocation);
            }
        }
        
        return locationList;
    }
    
    public List<com.qualcommlabs.usercontext.protocol.Location> getCurrentMarkedPointsList() {
        List<com.qualcommlabs.usercontext.protocol.Location> locationList = 
                convertLatLngToLocation(currentMarkedPointsList);
        
        return locationList;
    }
    
}

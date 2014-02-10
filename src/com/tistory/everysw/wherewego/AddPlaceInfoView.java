package com.tistory.everysw.wherewego;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class AddPlaceInfoView extends LinearLayout {

    public AddPlaceInfoView(Context context) {
        super(context);
        InitView();
    }  


    private void InitView() {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.add_new_place_info, this, false);
        addView(v);
    }

}

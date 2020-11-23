package com.example.danbidemo1;

import android.view.View;
import com.example.danbidemo1.adapter.ClinicAdapter;

public interface OnClinicCenterClickListner {
    public void OnItemClick(ClinicAdapter.ClinicViewHolder holder, View view, int position, String title);
}



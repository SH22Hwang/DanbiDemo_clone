package com.example.danbidemo1;

import android.view.View;
import com.example.danbidemo1.adapter.CounselorAdapter;

public interface OnClinicCounselorClickListener {
    public void OnItemClick(CounselorAdapter.CounselorViewHolder holder, View view, int position, String title);
}

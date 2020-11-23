package com.example.danbidemo1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.danbidemo1.data.CounsellingCenter;
import com.example.danbidemo1.OnClinicCenterClickListner;
import com.example.danbidemo1.R;
import com.example.danbidemo1.adapter.ClinicAdapter;
import com.example.danbidemo1.controllers.DanbiController;
import java.util.ArrayList;

/** 상담센터 리스트로 보여주는 화면(검색창 존재) */
public class ClinicList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ClinicAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<CounsellingCenter> arrayList;
    private Intent select_area_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_list);
        getSupportActionBar().hide();

        select_area_intent = getIntent();
        String select_area = select_area_intent.getStringExtra("Select_area");

        DanbiController danbiController = new DanbiController(this);
        danbiController.popupLoading();
        recyclerView = findViewById(R.id.clinic_recycler);
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<CounsellingCenter>(); // Clinic 객체를 담을 어레이 리스트 (어댑터 쪽으로)
        adapter = new ClinicAdapter(arrayList, this);
        danbiController.getFirebaseData(select_area, arrayList, adapter);
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결
        adapter.setOnItemClickListener(new OnClinicCenterClickListner() {
            @Override
            public void OnItemClick(ClinicAdapter.ClinicViewHolder holder, View view, int position, String title) {
                Intent intent = new Intent(getApplicationContext(), ClinicIntroduction.class);
                Log.d("test_position", position + "");
                Log.d("test_title", title + "");
                intent.putExtra("ClinicIndex", position);
                intent.putExtra("ClinicTitle", title); // 타이틀값을 ClinicIntroduction으로 보냄.
                intent.putExtra("Select_area", select_area); // 선택 지역값을 ClinicIntroduction으로 보냄.
                startActivity(intent);
            }
        });
    }
}
package com.example.danbidemo1;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.danbidemo1.controllers.DanbiController;
import java.util.ArrayList;

public class ClinicList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ClinicAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<CounsellingCenter> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_list);
        getSupportActionBar().hide();

        DanbiController danbiController = new DanbiController(this);
        danbiController.popupLoading();
        recyclerView = findViewById(R.id.clinic_recycler);
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<CounsellingCenter>(); // Clinic 객체를 담을 어레이 리스트 (어댑터 쪽으로)
        adapter = new ClinicAdapter(arrayList, this);
        danbiController.getFirebaseData("Danbi01", arrayList, adapter);
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결
        adapter.setOnItemClickListener(new OnClinicCenterClickListner() {
            @Override
            public void OnItemClick(ClinicAdapter.ClinicViewHolder holder, View view, int position, String title) {
                Intent intent = new Intent(getApplicationContext(), ClinicIntroduction.class);
                Log.d("test", position + "");
                Log.d("testtitle", title + "");
                intent.putExtra("ClinicIndex", position);
                intent.putExtra("ClinicTitle", title); // 타이틀 보냄.
                startActivity(intent);
            }
        });
    }
}
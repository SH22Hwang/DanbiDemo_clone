package com.example.danbidemo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ClinicList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Clinic> arrayList;

    FirebaseFirestore db = FirebaseFirestore.getInstance();// 파이어베이스 데이터베이스 연동

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_list);
        getSupportActionBar().hide();

        RelativeLayout loader = findViewById(R.id.layout_loading);
        loader.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.clinic_recycler);
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // Clinic 객체를 담을 어레이 리스트 (어댑터 쪽으로)


        db.collection("Danbi01") //파이어베이스에서 Danbi01 collection을 연결한다.
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            arrayList.clear(); //기존 배열리스트가 존재하지 않게 초기화시켜줌.
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Clinic clinic = document.toObject(Clinic.class);
                                arrayList.add(clinic); //데이터를 배열리스트에 담아 리사이클러 뷰로 보낼 준비
                                Log.d("ClinicList", document.getId() + "=>" + document.getData());
                            }

                            adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
                            loader.setVisibility(View.GONE);
                        } else {
                            Log.w("ClinicList", "Erro getting documents.", task.getException());
                        }
                    }
                });
        adapter = new ClinicAdapter(arrayList, this);
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결


//        notebookRef.get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        arrayList.clear(); //기존 배열리스트가 존재하지 않게 초기화시켜줌.
//                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
//                            Clinic clinic = documentSnapshot.toObject(Clinic.class);
//                            arrayList.add(clinic); // 데이터를 배열리스트에 담아 리사이클러 뷰로 보낼 준비
//                        }
//                        adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
//                    }
//                });
//
//
//        adapter = new ClinicAdapter(arrayList, this);
//        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결
    }
}
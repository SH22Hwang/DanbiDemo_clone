package com.example.danbidemo1.controllers;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.danbidemo1.adapter.ClinicAdapter;
import com.example.danbidemo1.data.CounsellingCenter;
import com.example.danbidemo1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Map;

/** 화면에 로딩을 띄우는 메소드와 파이어베이스에 데이터를 주고 받는 메소드를 포함한 클래스 */
public class DanbiController {
    private Activity activity;

    public DanbiController(Activity activity){
        this.activity = activity;
    }

    public void popupLoading(){
        RelativeLayout loader = activity.findViewById(R.id.layout_loading);
        loader.setVisibility(View.VISIBLE);
    }

    public void unableLoading(){
        RelativeLayout loader = activity.findViewById(R.id.layout_loading);
        loader.setVisibility(View.GONE);
    }

    public void popupException(String errorMsg){
        Toast.makeText(activity, errorMsg, Toast.LENGTH_SHORT).show();
    }

    public void getFirebaseData(String collectionPath, ArrayList arrayList, ClinicAdapter adapter){
        FirebaseFirestore db = FirebaseFirestore.getInstance();// 파이어베이스 데이터베이스 연동
        db.collection(collectionPath) //파이어베이스에서 Danbi01 collection을 연결한다.
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            arrayList.clear(); //기존 배열리스트가 존재하지 않게 초기화시켜줌.
                            for (QueryDocumentSnapshot document : task.getResult()){
                                CounsellingCenter counselingCenter = document.toObject(CounsellingCenter.class);
                                arrayList.add(counselingCenter); //데이터를 배열리스트에 담아 리사이클러 뷰로 보낼 준비
                                Log.d("ClinicList", document.getId() + "=>" + document.getData());
                            }
                            adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
                            unableLoading();
                        } else {
                            Log.w("ClinicList", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void setFirebaseData(String collectionPath, Map userdata){
        FirebaseFirestore db = FirebaseFirestore.getInstance();// 파이어베이스 데이터베이스 연동
        db.collection(collectionPath)
                .add(userdata)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("test", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("test", "Error adding document", e);
                    }
                });
    }
}

/** travid ci commit test*/

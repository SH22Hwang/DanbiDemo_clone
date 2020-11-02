package com.example.danbidemo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ClinicIntroduction extends AppCompatActivity {

 /*   private ArrayList<Clinic> arrayList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();// 파이어베이스 데이터베이스 연동*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_introduction);
        getSupportActionBar().hide();
 /*       Button button = findViewById(R.id.back_button);
        RatingBar ratingBar = findViewById(R.id.clinic_intro_rating);
        TextView rating_number = findViewById(R.id.rating_number);
        TextView expertise = findViewById(R.id.clinic_intro_expertise);
        TextView address = findViewById(R.id.clinic_intro_address);
        ImageView imageView = findViewById(R.id.clinic_profile);
        ratingBar.setRating(5.0f);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        button.setText("<" +" 상담소 이름" + " 프로필");
        address.setText("경남 진주시 대신로 475번길 6-8 2층 숨심리상담");
        rating_number.setText("5.0 (" + "150" + ")");
        expertise.setText("#가족상담 #부부상담 #아동상담");
*/

        /*
        Intent intent = getIntent();
        int position = intent.getExtras().getInt("ClinicIndex");
        Log.d("test2", position + "");
        *//*arrayList = new ArrayList<>(); // Clinic 객체를 담을 어레이 리스트 (어댑터 쪽으로)
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
                        } else {
                            Log.w("ClinicList", "Erro getting documents.", task.getException());
                        }
                    }
                });*/

    }
}
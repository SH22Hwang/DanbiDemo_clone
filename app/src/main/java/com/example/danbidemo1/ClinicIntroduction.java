package com.example.danbidemo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.danbidemo1.controllers.DanbiController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ClinicIntroduction extends AppCompatActivity {
    private Intent title_intent;

    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_clinic_introduction);
        DanbiController danbiController = new DanbiController(this);
        danbiController.popupLoading();

        ImageView iv_profile = findViewById(R.id.clinic_profile_img);
        TextView tv_clinicName = findViewById(R.id.clinic_intro_name_text);
        TextView tv_clinicExpertise = findViewById(R.id.clinic_intro_expertise);
        TextView tv_clinicAddress = findViewById(R.id.clinic_intro_address_text);
        TextView tv_clinicPhone = findViewById(R.id.clinic_intro_phoneNumber_text);
        TextView tv_email = findViewById(R.id.clinic_intro_email_text);
        TextView rating_number = findViewById(R.id.rating_number);
        RatingBar ratingBar = findViewById(R.id.clinic_intro_rating);
        Button button = findViewById(R.id.back_button);

        title_intent = getIntent();

        String clinic_title = title_intent.getStringExtra("ClinicTitle");
        button.setText("< " + clinic_title + " 프로필");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        CollectionReference clinicRef = rootRef.collection("Danbi01");
        Query titleQuery = clinicRef.whereEqualTo("clinic_name", clinic_title);

        titleQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        CounsellingCenter clinicData1 = documentSnapshot.toObject(CounsellingCenter.class);
                        String clinic_name = clinicData1.getName();
                        int clinic_rating = (int)clinicData1.getRating();
                        String clinic_expertise = clinicData1.getExpert();
                        String clinic_address = clinicData1.getAddress();
                        String clinic_phoneNumber = clinicData1.getContact();
                        String clinic_email = clinicData1.getEmail();

                        try{
                            //에러가 발생할 수 있는 코드
                            Glide.with(getApplicationContext())
                                    .load(clinicData1.getProfile())
                                    .into(iv_profile);

                        }catch (Exception e){
                            //에러시 수행
                            e.printStackTrace(); //오류 출력(방법은 여러가지)

                        }finally{
                            //무조건 수행
                        }

                        tv_clinicName.setText(clinic_name);
                        ratingBar.setRating(clinic_rating);
                        rating_number.setText((float)clinic_rating + "(150)");
                        tv_clinicExpertise.setText(addTag(clinic_expertise));
                        tv_clinicAddress.setText(clinic_address);
                        tv_clinicPhone.setText(clinic_phoneNumber);
                        tv_email.setText(clinic_email);
                        danbiController.unableLoading();
                        Log.d("ClinicIntroduction", documentSnapshot.getId() + "=>" + documentSnapshot.getData());
                    }
                }
            }
        });
    }
    private String addTag(String expert){
        String result = "";
        String temp[];
        temp = expert.split(",");

        for (int i = 0; i < temp.length; i++) {
            result += "#" + temp[i].trim() + " ";
        }

        return result;
    }
}
package com.example.danbidemo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
        setContentView(R.layout.activity_clinic_introduction);

        ImageView iv_profile = findViewById(R.id.clinic_profile);
        TextView tv_clinicName = findViewById(R.id.clinic_intro_name);
        TextView tv_clinicExpertise = findViewById(R.id.clinic_intro_expertise);
        TextView tv_clinicAddress = findViewById(R.id.clinic_intro_address);
        TextView tv_clinicPhone = findViewById(R.id.clinic_intro_phoneNumber);
        TextView tv_email = findViewById(R.id.clinic_intro_email);
        RatingBar ratingBar = findViewById(R.id.clinic_intro_rating);

        title_intent = getIntent();

        String clinic_title = title_intent.getStringExtra("ClinicTitle");

        CollectionReference clinicRef = rootRef.collection("Danbi01");
        Query titleQuery = clinicRef.whereEqualTo("clinic_name", clinic_title);
        titleQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        ClinicData1 clinicData1 = documentSnapshot.toObject(ClinicData1.class);
                        //String clinic_profile = clinicData1.getProfile();
                        String clinic_name = clinicData1.getClinic_name();
                        int clinic_rating = (int)clinicData1.getClinic_rating();
                        String clinic_expertise = clinicData1.getClinic_expertise();
                        String clinic_address = clinicData1.getClinic_address();
                        String clinic_phoneNumber = clinicData1.getClinic_phoneNumber();
                        String clinic_email = clinicData1.getClinic_email();

                        try{
                            //에러가 발생할 수 있는 코드
                            Glide.with(getApplicationContext())
                                    .load(documentSnapshot)
                                    .into(iv_profile);

                        }catch (Exception e){
                            //에러시 수행
                            e.printStackTrace(); //오류 출력(방법은 여러가지)

                        }finally{
                            //무조건 수행
                        }


                        tv_clinicName.setText(clinic_name);
                        ratingBar.setRating(clinic_rating);
                        tv_clinicExpertise.setText(clinic_expertise);
                        tv_clinicAddress.setText(clinic_address);
                        tv_clinicPhone.setText(clinic_phoneNumber);
                        tv_email.setText(clinic_email);

                        Log.d("ClinicIntroduction", documentSnapshot.getId() + "=>" + documentSnapshot.getData());
                    }
                }
            }
        });

    }
}
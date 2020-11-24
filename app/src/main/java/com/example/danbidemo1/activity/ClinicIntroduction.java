package com.example.danbidemo1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.danbidemo1.OnClinicCenterClickListner;
import com.example.danbidemo1.OnClinicCounselorClickListener;
import com.example.danbidemo1.R;
import com.example.danbidemo1.adapter.ClinicAdapter;
import com.example.danbidemo1.adapter.CounselorAdapter;
import com.example.danbidemo1.controllers.CounselorController;
import com.example.danbidemo1.controllers.DanbiController;
import com.example.danbidemo1.data.CounsellingCenter;
import com.example.danbidemo1.data.Counselor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/** 상담센터 상세 정보를 보여주는 액티비티 화면 */
public class ClinicIntroduction extends AppCompatActivity {
    private Intent title_intent;
    private Intent select_area_intent;
    private RecyclerView recyclerView;
    private CounselorAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Counselor> arrayList;

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

        CounselorController counselorController = new CounselorController(this);
        danbiController.popupLoading();
        recyclerView = findViewById(R.id.counselor_recycler);
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<Counselor>(); // Clinic 객체를 담을 어레이 리스트 (어댑터 쪽으로)
        adapter = new CounselorAdapter(arrayList, this);
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결

        //임시 개발중..
        adapter.setOnItemClickListener(new OnClinicCounselorClickListener() {
            @Override
            public void OnItemClick(CounselorAdapter.CounselorViewHolder holder, View view, int position, String title) {
                Intent intent = new Intent(getApplicationContext(), ClinicIntroduction.class);
                Log.d("test_position", position + "");
                Log.d("test_title", title + "");
                //intent.putExtra("ClinicIndex", position);
                //intent.putExtra("ClinicTitle", title); // 타이틀값을 ClinicIntroduction으로 보냄.
                //intent.putExtra("Select_area", select_area); // 선택 지역값을 ClinicIntroduction으로 보냄.
                //startActivity(intent);
            }
        });
        //개발 중..

        title_intent = getIntent();
        select_area_intent = getIntent();
        String select_area = select_area_intent.getStringExtra("Select_area"); //선택 지역 값 intent로 받아옴.
        String clinic_title = title_intent.getStringExtra("ClinicTitle"); //선택된 상담센터 타이틀 값 intent로 받아옴.
        button.setText("< " + clinic_title + " 프로필");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        CollectionReference clinicRef = rootRef.collection(select_area);
        Query titleQuery = clinicRef.whereEqualTo("name", clinic_title);

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
                        Log.d("컬렉션 주소값 테스트", select_area+"/"+documentSnapshot.getId()+"/"+"Counselor");
                        counselorController.getFirebaseData(select_area+"/"+documentSnapshot.getId()+"/"+"Counselor", arrayList, adapter);
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
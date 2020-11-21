package com.example.danbidemo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final int CLINIC_LIST = 1001; //다른 액티비티 띄우기 위한 요청 코드
    public static final int CLINIC_CHANG_LIST = 1002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        Button chang = (Button)findViewById(R.id.select_changwon_button); // 창원 지역 버튼
        Button jin = (Button)findViewById(R.id.select_jinju_button); // 진주 지역 버튼

        BtnOnClick onClick = new BtnOnClick(ClinicChangList.class);
        chang.setOnClickListener(onClick);
        onClick = new BtnOnClick(ClinicList.class);
        jin.setOnClickListener(onClick);

    }
    class BtnOnClick implements View.OnClickListener {
        private Class c;
        BtnOnClick(Class c) {
            this.c = c;
        }
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), c); //intent를 사용하여 상담센터(진주)리스트 화면으로 넘어간다.
            startActivityForResult(intent, CLINIC_LIST);
        }

    }
}

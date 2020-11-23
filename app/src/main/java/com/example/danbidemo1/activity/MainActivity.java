package com.example.danbidemo1.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.danbidemo1.R;

/** 지역 선택 화면 */
public class MainActivity extends AppCompatActivity {

    public static final int CLINIC_LIST = 1001; //다른 액티비티 띄우기 위한 요청 코드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        Button jin = (Button)findViewById(R.id.select_jinju_button); // 진주 지역 버튼
        Button chang = (Button)findViewById(R.id.select_changwon_button); // 창원 지역 버튼
        BtnOnClickOfJinju onClickJ = new BtnOnClickOfJinju(ClinicList.class);
        BtnOnClickOfChanwon onClickC = new BtnOnClickOfChanwon(ClinicList.class);
        jin.setOnClickListener(onClickJ);
        chang.setOnClickListener(onClickC);
    }

    class BtnOnClickOfJinju implements View.OnClickListener {
        private Class c;

        BtnOnClickOfJinju(Class c) {
            this.c = c;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), c); //intent를 사용하여 상담센터(진주)리스트 화면으로 넘어간다.
            Log.d("테스트 버튼 값", view + "");
            intent.putExtra("Select_area", "Danbi01"); // 컬렉션 이름 보냄
            startActivityForResult(intent, CLINIC_LIST);
        }
    }

    class BtnOnClickOfChanwon implements View.OnClickListener {
        private Class c;

        BtnOnClickOfChanwon(Class c) {
            this.c = c;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), c); //intent를 사용하여 상담센터(진주)리스트 화면으로 넘어간다.
            Log.d("테스트 버튼 값", view + "");
            intent.putExtra("Select_area", "ChangWon"); // 컬렉션 이름 보냄
            startActivityForResult(intent, CLINIC_LIST);
        }
    }
}

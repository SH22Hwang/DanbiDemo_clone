package com.example.danbidemo1.ui.login;

import androidx.annotation.NonNull;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.example.danbidemo1.MainActivity;
import com.example.danbidemo1.R;
import com.example.danbidemo1.controllers.DanbiController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DanbiController danbiController;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        danbiController = new DanbiController(this);
        final EditText usernameEditText = findViewById(R.id.username_edittext);
        final EditText passwordEditText = findViewById(R.id.password_edittext);
        final Button login_Button = findViewById(R.id.login_button);
        final TextView register_button = findViewById(R.id.register_button);
        final CheckBox saveIdPw_checkbox = findViewById(R.id.saveidpw_checkbox);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();

        login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                Log.d("test", "login attempt");
                Log.d("test", email);

                if(TextUtils.isEmpty(email) || email.contains(" ")) {
                    danbiController.popupException("아이디에 공백값이 있습니다.");
                }
                else{
                    if (TextUtils.isEmpty(password) || password.contains(" ")) {
                        danbiController.popupException("비밀번호에 공백값이 있습니다.");
                    }
                    else {
                        if (password.length() < 6) {
                            danbiController.popupException("비밀번호는 6자리 이상이어야 합니다!");
                        }
                        else {
                            logIn(email, password);
                        }
                    }
                }
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test", "onclick");
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

        private void logIn (String email, String password){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.d("test", "login attempt inside ");
                                Log.d("test", "signInWithEmail:success");
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                danbiController.popupException("로그인에 실패했습니다.");
                                Log.d("test", "login attempt inside");
                                Log.w("test", "signInWithEmail:failure", task.getException());
                            }
                        }
                    });
        }
//        private boolean getCurrentUser(){
//
//            if (user != null) {
//                // Name, email address, and profile photo Url
//                String name = user.getDisplayName();
//                String email = user.getEmail();
//                boolean emailVerified = user.isEmailVerified();
//
//                // The user's ID, unique to the Firebase project. Do NOT use this value to
//                // authenticate with your backend server, if you have one. Use
//                // FirebaseUser.getIdToken() instead.
//                String uid = user.getUid();
//                return emailVerified;
//            }
//            return false;
//        }
}

package com.example.danbidemo1.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.danbidemo1.R;
import com.example.danbidemo1.controllers.DanbiController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.HashMap;
import java.util.Map;

/** 회원가입 화면 액티비티 */
public class RegistrationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DanbiController danbiController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        danbiController = new DanbiController(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        mAuth = FirebaseAuth.getInstance();
        Button btn_signUp = findViewById(R.id.signup_button);
        EditText user_name = findViewById(R.id.edit_name_edittext);
        EditText user_id = findViewById(R.id.edit_username_edittext);
        EditText user_password = findViewById(R.id.edit_password_edittext);
        EditText user_passTemp = findViewById(R.id.edit_checkpw_edittext);
        getSupportActionBar().hide();

        btn_signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String name = user_name.getText().toString();
                String id = user_id.getText().toString();
                String password = user_password.getText().toString();
                String password_check = user_passTemp.getText().toString();

                if(TextUtils.isEmpty(name) || id.contains(" ")) {
                    danbiController.popupException("이름에 공백값이 있습니다.");
                }
                else{
                    if (TextUtils.isEmpty(id) || id.contains(" ")) {
                        danbiController.popupException("아이디에 공백값이 있습니다.");
                    }
                    else {
                        if (!password.equals(password_check)) {
                            danbiController.popupException("비밀번호가 같지 않습니다!");
                        }
                        else {
                            if (TextUtils.isEmpty(password) || password.contains(" ")) {
                                danbiController.popupException("비밀번호에 공백값이 있습니다.");
                            }
                            else {
                                if (password.length() < 6) {
                                    danbiController.popupException("비밀번호는 6자리 이상이어야 합니다!");
                                }
                                else {
                                    SignUp(id, password, name);
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private void SignUp(String id, String password, String name) {
        mAuth.createUserWithEmailAndPassword(id, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("test", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            danbiController.popupException("회원가입에 성공했습니다.");
                            Map<String, Object> userdata = new HashMap<>();
                            userdata.put("id", user);
                            userdata.put("name", name);
                            danbiController.setFirebaseData("users", userdata);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("test", "createUserWithEmail:failure", task.getException());
                            danbiController.popupException("회원가입에 실패했습니다.");
                            //updateUI(null);
                        }
                    }
                });
    }
}




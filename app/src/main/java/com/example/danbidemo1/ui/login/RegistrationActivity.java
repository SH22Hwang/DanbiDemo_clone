package com.example.danbidemo1.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.danbidemo1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                    Toast.makeText(RegistrationActivity.this, "이름에 공백값이 있습니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (TextUtils.isEmpty(id) || id.contains(" ")) {
                        Toast.makeText(RegistrationActivity.this, "아이디에 공백값이 있습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (!password.equals(password_check)) {
                            Toast.makeText(RegistrationActivity.this, "비밀번호가 같지 않습니다!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (TextUtils.isEmpty(password) || password.contains(" ")) {
                                Toast.makeText(RegistrationActivity.this, "비밀번호에 공백값이 있습니다.", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if (password.length() < 6) {
                                    Toast.makeText(RegistrationActivity.this, "비밀번호는 6자리 이상이어야 합니다!", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(RegistrationActivity.this, "회원가입에 성공했습니다.",Toast.LENGTH_SHORT).show();
                            Map<String, Object> userdata = new HashMap<>();
                            userdata.put("id", user);
                            userdata.put("name", name);

                            db.collection("users")
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
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("test", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationActivity.this, "회원가입에 실패했습니다.",Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

}




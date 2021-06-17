package com.sgp.fruitinspector;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {
    EditText name,email,password,phone;
    Button signUp;
    private FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.Name);
        email= findViewById(R.id.EmailAddress2);
        phone = findViewById(R.id.Phone);
        password = findViewById(R.id.Password2);
        signUp = findViewById(R.id.signup1);
        auth = FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String rname = name.getText().toString().trim();
                final String remail = email.getText().toString().trim();
                final String rphone = phone.getText().toString().trim();
                String rpassword = password.getText().toString().trim();
                if(TextUtils.isEmpty(rname) || TextUtils.isEmpty(remail) || TextUtils.isEmpty(rphone) || TextUtils.isEmpty(rpassword)){
                    Toast.makeText(signup.this,"All Fields are Required",Toast.LENGTH_LONG).show();
                }
                else {
                    auth.createUserWithEmailAndPassword(remail,rpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                 Toast.makeText(signup.this, "Signed Up Successful!!", Toast.LENGTH_LONG).show();
                                userID=auth.getCurrentUser().getUid();
                                Map<String,String> map = new HashMap<>();
                                map.put("User_Name",rname);
                                map.put("User_Email",remail);
                                map.put("User_Phone",rphone);
                                firebaseFirestore.collection("users").document(userID).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(signup.this,"User successfully added",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        String error=e.getMessage();
                                        Toast.makeText(signup.this,"Error msg: "+error,Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Intent intent=new Intent(getApplicationContext(),CaptureImage.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(signup.this, "Signed Up Failed!!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
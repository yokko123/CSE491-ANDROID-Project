package com.ecom.khawadawa_main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class VendorSignUp extends AppCompatActivity {
    EditText shopNameVendor;
    EditText emailVendor;
    EditText phoneVendor;
    EditText passwordVendor;
    Button signUpVendor;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_sign_up);

        //hooks
        shopNameVendor = findViewById(R.id.shopNameVendor);
        emailVendor = findViewById(R.id.emailVendor);
        phoneVendor = findViewById(R.id.phoneVendor);
        passwordVendor = findViewById(R.id.passwordVendor);
        signUpVendor = findViewById(R.id.signUpVendor);



        firebaseAuth = FirebaseAuth.getInstance();


        signUpVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailTextVendor = emailVendor.getText().toString().trim();
                String passwordTextVendor = passwordVendor.getText().toString().trim();

                if(TextUtils.isEmpty(emailTextVendor)){
                    Toast.makeText(VendorSignUp.this, "Please enter your email address", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(passwordTextVendor)){
                    Toast.makeText(VendorSignUp.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                }

                firebaseAuth.createUserWithEmailAndPassword(emailTextVendor, passwordTextVendor)
                        .addOnCompleteListener(VendorSignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    addVendor();
                                    startActivity(new Intent(VendorSignUp.this,VendorDashboard.class));
                                    finish();

                                } else {
                                    Toast.makeText(VendorSignUp.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                                     }
                            }
                        });
            }

        });
    }

    private void addVendor() {

        String shopName = shopNameVendor.getText().toString().trim();
        String mailVendor = emailVendor.getText().toString().trim();
        String phoneVen = phoneVendor.getText().toString().trim();
        String passwordVen = passwordVendor.getText().toString().trim();

        if(!TextUtils.isEmpty(mailVendor)){

            HashMap<String, Object> hashMap1 = new HashMap<>();
            hashMap1.put("uId",""+firebaseAuth.getUid());
            hashMap1.put("shopName",""+shopName);
            hashMap1.put("vendorMail",""+mailVendor);
            hashMap1.put("vendorPhone",""+phoneVen);
            hashMap1.put("vendorPassword",""+passwordVen);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("vendor");
            //String id = ref.push().getKey();
            ref.child(firebaseAuth.getUid()).setValue(hashMap1)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(VendorSignUp.this, "Shop added", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(VendorSignUp.this, "error occured", Toast.LENGTH_SHORT).show();

                        }
                    });
        }

    }
}
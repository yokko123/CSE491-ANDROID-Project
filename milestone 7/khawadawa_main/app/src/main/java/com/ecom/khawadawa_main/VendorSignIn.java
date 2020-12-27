package com.ecom.khawadawa_main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class VendorSignIn extends AppCompatActivity {

    EditText vendorEmail;
    EditText vendorPassword;
    Button vendorSignInButton;
    FirebaseAuth mAuth2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_sign_in);


        mAuth2 = FirebaseAuth.getInstance();

        //Hooks
        vendorEmail = findViewById(R.id.signInVendorEmail);
        vendorPassword = findViewById(R.id.vendorPassword);
        vendorSignInButton = findViewById(R.id.vendorSignInButton);


        vendorSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cendorEmailText = vendorEmail.getText().toString().trim();
                String vendorPasswordText = vendorPassword.getText().toString().trim();

                mAuth2.signInWithEmailAndPassword(cendorEmailText, vendorPasswordText)
                        .addOnCompleteListener(VendorSignIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                   startActivity(new Intent(VendorSignIn.this,VendorDashboard.class));
                                   finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(VendorSignIn.this, "Incorrect Email or Password! Please Try again.", Toast.LENGTH_SHORT).show();
                                    // ...
                                }

                                // ...
                            }
                        });


            }
        });
    }
}
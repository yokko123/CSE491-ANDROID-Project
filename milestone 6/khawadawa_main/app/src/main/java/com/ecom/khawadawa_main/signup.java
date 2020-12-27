  package com.ecom.khawadawa_main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class signup extends AppCompatActivity {
    Button signup;
    Button googleSignUp;
    EditText emailText;
    EditText passwordText;
    TextView signInButton;
    EditText name;
    EditText phone;
    FirebaseAuth mAuth;
    TextView vendorSignUp;
    private DatabaseReference mDatabase;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 1;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();

        if(user!=null){
            Intent intent = new Intent(signup.this,Dashboard.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("user");

        creatRequest();

        googleSignUp = findViewById(R.id.googleSignUp);
        signup = findViewById(R.id.signupButton);
        emailText = findViewById(R.id.email);
        vendorSignUp = findViewById(R.id.vendorSignUp);
        passwordText = findViewById(R.id.password);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);






        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(signup.this, "Please Enter Your Email Id", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(password)){
                    Toast.makeText(signup.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(password.length()<=6){
                    Toast.makeText(signup.this, "Password Required minimum 8 digits", Toast.LENGTH_SHORT).show();
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    addUser();
                                    Intent intent = new Intent(signup.this,Dashboard.class);
                                    Toast.makeText(signup.this, "Signup successful", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(signup.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }



        });

        signInButton = findViewById(R.id.signinbutton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this,SignIn.class);
                startActivity(intent);
            }
        });

        googleSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        vendorSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signup.this,VendorSignUp.class));
            }
        });



    }

    private void creatRequest() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            addUserGoogle();
                            Intent intent = new Intent(signup.this,Dashboard.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(signup.this, "sorry authentication failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void addUser(){
        String email = emailText.getText().toString().trim();
        String nameText = name.getText().toString().trim();
        String phoneText = phone.getText().toString().trim();

        if(!TextUtils.isEmpty(nameText)){

            HashMap<String, Object> hashMap1 = new HashMap<>();
            hashMap1.put("uId",""+mAuth.getUid());
            hashMap1.put("email",""+email);
            hashMap1.put("Name",""+nameText);
            hashMap1.put("phone",""+phoneText);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user");
            //String id = ref.push().getKey();
            ref.child(mAuth.getUid()).setValue(hashMap1)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(signup.this, "Shop added", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(signup.this, "error occured", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
        else{
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
        }

    }

    private void addUserGoogle(){
         GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(signup.getContext());
         if(acct!=null){
             String name  = acct.getDisplayName();
             String email = acct.getEmail();
             String phone = null;

             HashMap<String, Object> hashMap1 = new HashMap<>();
             hashMap1.put("uId",""+mAuth.getUid());
             hashMap1.put("email",""+email);
             hashMap1.put("Name",""+name);
             hashMap1.put("phone",""+phone);

             DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user");
             //String id = ref.push().getKey();
             ref.child(mAuth.getUid()).setValue(hashMap1)
                     .addOnSuccessListener(new OnSuccessListener<Void>() {
                         @Override
                         public void onSuccess(Void aVoid) {

                             Toast.makeText(signup.this, "Shop added", Toast.LENGTH_SHORT).show();

                         }
                     })
                     .addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Toast.makeText(signup.this, "error occured", Toast.LENGTH_SHORT).show();

                         }
                     });

         }
         else{
             Toast.makeText(this, "Error orccured", Toast.LENGTH_SHORT).show();
         }

    }
}

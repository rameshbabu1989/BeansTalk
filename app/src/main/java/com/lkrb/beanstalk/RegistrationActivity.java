package com.lkrb.beanstalk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {
    private static String TAG= "RegistrationActivity >>> ";

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    private TextView mNavSignInScreen;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mConfPasswordView;
    private EditText mPhoneView;
    private EditText mNameView;
    private EditText mAddrLine1;
    private EditText mAddrLine2;
    private EditText mAddrCity;
    private EditText mAddrState;
    private EditText mAddrCountry;
    private EditText mAddrPincode;
    private EditText mCardName;
    private EditText mCardNumber;
    private EditText mCardCvv;

    private Button mConfirmSignUp;
    private Button mContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mNameView = findViewById(R.id.reg_name);
        mEmailView = findViewById(R.id.reg_email);
        mPasswordView = findViewById(R.id.reg_password);
        mConfPasswordView = findViewById(R.id.reg_password_conf);
        mPhoneView = findViewById(R.id.reg_phone);
        mNameView = findViewById(R.id.reg_name);

        // address
        mAddrLine1 = findViewById(R.id.reg_addr_line1);
        mAddrLine2 = findViewById(R.id.reg_addr_line2);
        mAddrCity = findViewById(R.id.reg_addr_city);
        mAddrState = findViewById(R.id.reg_addr_state);
        mAddrCountry = findViewById(R.id.reg_addr_country);
        mAddrPincode = findViewById(R.id.reg_addr_pincode);

        // card
        mCardName = findViewById(R.id.reg_card_name);
        mCardNumber = findViewById(R.id.reg_card_number);
        mCardCvv = findViewById(R.id.reg_card_cvv);


        mNavSignInScreen = findViewById(R.id.nav_signin);
        mNavSignInScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        mConfirmSignUp = findViewById(R.id.email_sign_up_button);
        mConfirmSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    public void signUp(){
        Log.d(TAG, "signUp:called");
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();
        final String passwordConf = mPasswordView.getText().toString();

        final String name = mNameView.getText().toString();
        final String phone = mPhoneView.getText().toString();

        final String line1 = mAddrLine1.getText().toString();
        final String line2 = mAddrLine1.getText().toString();
        final String city = mAddrCity.getText().toString();
        final String state = mAddrState.getText().toString();
        final String country = mAddrCountry.getText().toString();
        final String pincode = mAddrPincode.getText().toString();

        final String holderName = mCardName.getText().toString();
        final String number = mCardNumber.getText().toString();
        final String cvv = mCardCvv.getText().toString();
        if(password != passwordConf){
            Toast.makeText(RegistrationActivity.this, "Passwords MisMatch",
                    Toast.LENGTH_SHORT).show();
        }
        if(validString(email) && validString(password) && validString(name) && validString(phone) &&
                validString(line1) && validString(line2) && validString(city) && validString(state) &&
                validString(country) && validString(pincode) && validString(holderName) && validString(number) && validString(cvv)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                HashMap<String, Object> address = new HashMap<>();
                                address.put("line1", line1);
                                address.put("line2", line2);
                                address.put("city", city);
                                address.put("state", state);
                                address.put("country", country);
                                address.put("pincode", pincode);

                                HashMap<String, Object> cardDetail = new HashMap<>();
                                cardDetail.put("name", holderName);
                                cardDetail.put("number", number);
                                cardDetail.put("cvv", cvv);

                                HashMap<String, Object> userProfile = new HashMap<>();
                                userProfile.put("basic", user);
                                userProfile.put("name", name);
                                userProfile.put("phone", phone);
                                userProfile.put("phone", email);
                                userProfile.put("address", address);
                                userProfile.put("card", cardDetail);
                                mDatabase.child("users").child(user.getUid()).setValue(userProfile);
                                checkUserSession();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegistrationActivity.this, "failed to create account"+task.getException(),
                                        Toast.LENGTH_SHORT).show();
                                Log.i(TAG, "onComplete: fail");
                            }
                        }
                    });
        } else {
            Toast.makeText(RegistrationActivity.this, "Please fill all fields",
                    Toast.LENGTH_SHORT).show();
        }
    }

    boolean validString(String string){
        if(string==null || string.isEmpty()){
            return false;
        } else {
            return true;
        }
    }

    public void checkUserSession(){
        if(mAuth.getCurrentUser()!=null){
            Toast.makeText(RegistrationActivity.this, "Successfully login...!",
                    Toast.LENGTH_SHORT).show();
            Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}

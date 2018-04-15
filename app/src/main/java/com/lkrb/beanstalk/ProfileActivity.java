package com.lkrb.beanstalk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private static String TAG= "ProfileActivity >>> ";


    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "onDataChange: "+dataSnapshot.getValue());
                Customer customer=dataSnapshot.getValue(Customer.class);
                Log.i(TAG, "onDataChange: "+customer.toString());
                TextView nameTxt=findViewById(R.id.tv_profile_name);
                TextView emailTxt=findViewById(R.id.tv_profile_email);
                TextView phoneTxt=findViewById(R.id.tv_profile_phone);
                nameTxt.setText(customer.getName());
                emailTxt.setText(user.getEmail());
                phoneTxt.setText(customer.getPhone());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

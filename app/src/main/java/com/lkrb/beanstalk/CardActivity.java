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

public class CardActivity extends AppCompatActivity {
    private static String TAG= "CardActivity >>> ";


    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        mAuth = FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "onDataChange: "+dataSnapshot.getValue());
                Customer customer=dataSnapshot.getValue(Customer.class);
                Log.i(TAG, "onDataChange: "+customer.toString());
                TextView nameTxt=findViewById(R.id.tv_card_name);
                TextView numberTxt=findViewById(R.id.tv_card_number);
                TextView cvvTxt=findViewById(R.id.tv_card_cvv);
                nameTxt.setText(customer.getCard().get("name").toString());
                numberTxt.setText(customer.getCard().get("number").toString());
                cvvTxt.setText(customer.getCard().get("cvv").toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

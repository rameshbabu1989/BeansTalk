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

public class AddressActivity extends AppCompatActivity {
    private static String TAG= "CardActivity >>> ";


    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        mAuth = FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Customer customer=dataSnapshot.getValue(Customer.class);
                Log.i(TAG, "onDataChange: "+customer.toString());

                TextView line1Txt=findViewById(R.id.tv_address_line1);
                TextView line2Txt=findViewById(R.id.tv_address_line2);
                TextView cityTxt=findViewById(R.id.tv_address_city);
                TextView stateTxt=findViewById(R.id.tv_address_state);
                TextView countryTxt=findViewById(R.id.tv_address_country);
                TextView pincodeTxt=findViewById(R.id.tv_address_pincode);

                line1Txt.setText(customer.getAddress().get("line1").toString());
                line2Txt.setText(customer.getAddress().get("line2").toString());
                cityTxt.setText(customer.getAddress().get("city").toString());
                stateTxt.setText(customer.getAddress().get("state").toString());
                countryTxt.setText(customer.getAddress().get("country").toString());
                pincodeTxt.setText(customer.getAddress().get("pincode").toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

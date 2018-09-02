package com.techhome.techhome.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techhome.techhome.R;

public class MainActivity extends AppCompatActivity {

    private Button btnOne, btnTwo, btnThree, btnFour, signOut;

    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    private Boolean one = false, two = false, three = false, four = false;

    DatabaseReference udatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        btnOne = (Button) findViewById(R.id.one_button);
        btnTwo = (Button) findViewById(R.id.two_button);
        btnThree = (Button) findViewById(R.id.three_button);
        btnFour = (Button) findViewById(R.id.four_button);
        signOut = (Button) findViewById(R.id.sign_out);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        assert user != null;
        final String uid = user.getUid();
        Log.e("UID ", uid);
        udatabase = FirebaseDatabase.getInstance().getReference("user").child(uid);

        udatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if(snapshot.child("switch_one").getValue().toString().equals("1")){
                    Log.e("Switch One", snapshot.child("switch_one").getValue().toString());
                    one = true;
                    btnOne.setBackgroundColor(Color.rgb(218,65,64));
                }
                else {
                    btnOne.setBackgroundColor(Color.rgb(236, 239, 241));
                }

                if(snapshot.child("switch_two").getValue().toString().equals("1")){
                    Log.e("Switch Two", snapshot.child("switch_two").getValue().toString());
                    two = true;
                    btnTwo.setBackgroundColor(Color.rgb(218,65,64));
                }
                else {
                    btnTwo.setBackgroundColor(Color.rgb(236, 239, 241));
                }

                if(snapshot.child("switch_three").getValue().toString().equals("1")){
                    Log.e("Switch Three", snapshot.child("switch_three").getValue().toString());
                    btnThree.setBackgroundColor(Color.rgb(218,65,64));
                    three = true;
                }
                else {
                    btnThree.setBackgroundColor(Color.rgb(236, 239, 241));
                }

                if(snapshot.child("switch_four").getValue().equals("1")){
                    Log.e("Switch Four", snapshot.child("switch_four").getValue().toString());
                    btnFour.setBackgroundColor(Color.rgb(218,65,64));
                    four = true;
                }
                else{
                    btnFour.setBackgroundColor(Color.rgb(236, 239, 241));
                }

//                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//                }
                //creating adapter
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status;
                if (one) {
                    one = false;
                    status = "0";
                    Log.e("Status", status);
                }
                else{
                    status = "1";
                    Log.e("Status",status);
                }
                FirebaseDatabase.getInstance().getReference("user").child(uid).child("switch_one").setValue(status);
                Toast.makeText(getApplicationContext(),"Change button", Toast.LENGTH_SHORT).show();
            }
        });


        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status;
                if (two){
                    two = false;
                    status = "0";
                }
                else {
                    status = "1";
                }
                FirebaseDatabase.getInstance().getReference("user").child(uid).child("switch_two").setValue(status);
            }
        });


        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status;
                if (three){
                    three = false;
                    status = "0";
                }
                else {
                    status = "1";
                }
                FirebaseDatabase.getInstance().getReference("user").child(uid).child("switch_three").setValue(status);
            }
        });

        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status;
                if (four){
                    four = false;
                    status = "0";
                }
                else {
                    status = "1";
                }
                FirebaseDatabase.getInstance().getReference("user").child(uid).child("switch_four").setValue(status);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}

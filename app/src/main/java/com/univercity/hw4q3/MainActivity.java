package com.univercity.hw4q3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private EditText Username;
    private EditText Phone;
    private Button Add;
    private DatabaseReference mRootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username=findViewById(R.id.username);
        Phone=findViewById(R.id.phone);
        Add=findViewById(R.id.add);
        mRootRef= FirebaseDatabase.getInstance().getReference();

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtUsername = Username.getText().toString().trim();
                String txtPhone = Phone.getText().toString().trim();
                addContent(txtUsername,txtPhone);
                
            }
        });
    }

    private void addContent(String txtUsername, String txtPhone) {
        if (TextUtils.isEmpty(txtUsername) || TextUtils.isEmpty(txtPhone))
            Toast.makeText(MainActivity.this, "Please Enter All Information", Toast.LENGTH_LONG).show();

        else if (txtPhone.length() < 11)
            Toast.makeText(MainActivity.this, "Phone Number Is Not Valid ", Toast.LENGTH_LONG).show();

        else {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", txtUsername);
            map.put("contact", txtPhone);
            mRootRef.child("Contact").push().setValue(map)

                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Create Contact is Successful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }


    }
}

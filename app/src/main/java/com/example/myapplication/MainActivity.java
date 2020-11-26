package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText personNameTxt, phoneTxt;
    Button signOutBtn, addBtn;
    ListView personListView;
    private List<Person> listNames = new ArrayList<Person>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayAdapter arrayAdapterPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        personNameTxt = findViewById(R.id.personNameTxt);
        phoneTxt = findViewById(R.id.phoneTxt);
        addBtn = findViewById(R.id.addBtn);
        personListView = findViewById(R.id.personListView);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("names");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listNames.clear();
                for(DataSnapshot snap : snapshot.getChildren()) {
                    Person str = snap.getValue(Person.class);
                    listNames.add(str);
                    arrayAdapterPerson = new ArrayAdapter<Person>(MainActivity.this, android.R.layout.simple_list_item_1, listNames);
                    personListView.setAdapter(arrayAdapterPerson);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(personNameTxt.getText().toString().trim())){
                    personNameTxt.setError("Required");
                    return;
                }
                if(!TextUtils.isDigitsOnly(phoneTxt.getText())){
                    phoneTxt.setError("Only numbers");
                    return;
                }
                Person newPerson = new Person();
                newPerson.setName(personNameTxt.getText().toString());
                newPerson.setPhoneNumber(Integer.parseInt(phoneTxt.getText().toString()));
                firebaseDatabase.getReference("names").push().setValue(newPerson)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
            }
        });

        signOutBtn = findViewById(R.id.signOutBtn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
    }
}
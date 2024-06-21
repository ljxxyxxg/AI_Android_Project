package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class SubActivity extends AppCompatActivity {
    Button button5;
    private DatabaseReference mDatabase;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> mDataList;

    private boolean sortByRecent = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("id_list");

        // Initialize views and adapter
        mDataList = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mDataList);
        ListView listView = findViewById(R.id.db_list_view);
        listView.setAdapter(mAdapter);

        // Load data from Firebase
        loadDataFromFirebase();

        // Button to manually reload data
        Button btnSelect = findViewById(R.id.btn_select);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDataFromFirebase();
            }
        });

        CheckBox checkBox = findViewById(R.id.check_time);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sortByRecent = isChecked;
                loadDataFromFirebase();
            }
        });

        button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadDataFromFirebase() {
        Query query = mDatabase.orderByChild("timestamp");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDataList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.child("id").getValue(String.class);
                    String name = snapshot.child("name").getValue(String.class);
                    long age = snapshot.child("age").getValue(Long.class);
                    String gender = snapshot.child("gender").getValue(String.class);
                    String timestamp = snapshot.child("timestamp").getValue(String.class);
                    //long timestampLong = Long.parseLong(timestamp);
                    //Date date = new Date(timestamp); // 시간값으로 변환된 timestamp

                    String data = "ID: " + id + ", Name: " + name + ", Age: " + age + ", Gender: " + gender + ", time: " + timestamp;
                    mDataList.add(data);
                }
                if (sortByRecent) {
                    Collections.reverse(mDataList);
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("MainActivity", "Failed to load data.", databaseError.toException());
                Toast.makeText(SubActivity.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


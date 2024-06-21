package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.TrafficEvent;
import com.example.myapplication.network.ApiResponse;
import com.example.myapplication.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubActivity2 extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<TrafficEvent> trafficEvents = new ArrayList<>();
    private ApiService apiService;
    private Button refreshButton;
    private Button button5;

    // Replace with your actual API key
    private static final String API_KEY = "85e4043f24744ee3be45fe07b76163f4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub2);

        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchTrafficEvents();
            }
        });

        button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료
            }
        });

        // Retrofit 객체 생성
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://openapi.its.go.kr:9443/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Retrofit 인터페이스 구현체 생성
        apiService = retrofit.create(ApiService.class);

        // 최초 데이터 요청
        fetchTrafficEvents();
    }

    private void fetchTrafficEvents() {
        Call<ApiResponse> call = apiService.getTrafficEvents(API_KEY, "all", "acc", "json");
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getBody() != null) {
                        List<TrafficEvent> events = apiResponse.getBody().getItems();
                        trafficEvents.clear();
                        trafficEvents.addAll(events);
                        updateListView();
                    } else {
                        Log.e("SubActivity2", "Response body is null or empty");
                        Toast.makeText(SubActivity2.this, "No data available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("SubActivity2", "Failed to fetch data");
                    Toast.makeText(SubActivity2.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("SubActivity2", "Error fetching data", t);
                Toast.makeText(SubActivity2.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateListView() {
        List<String> eventStrings = new ArrayList<>();
        for (TrafficEvent event : trafficEvents) {
            String eventString = "사고장소: " + event.getType() + " --> " + event.getEventType() + "\n"
                    + "사고위치: " + event.getRoadName() + "\n"
                    + "사고현황: " + event.getMessage();
            eventStrings.add(eventString);
        }
        adapter.clear();
        adapter.addAll(eventStrings);
        adapter.notifyDataSetChanged();
    }
}

package com.example.myapplication;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private static final String SERVER_IP = "192.168.1.100"; // 파이썬 서버의 IP 주소
    private static final int SERVER_PORT = 12345; // 파이썬 서버가 수신 대기하는 포트 번호
    private TextView textView;
    private Socket socket;
    private BufferedReader bufferedReader;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                // 소켓에서 받은 데이터를 텍스트뷰에 표시
                String data = (String) msg.obj;
                textView.setText(data);
                return true;
            }
        });

        // 소켓 연결 스레드 시작
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 소켓 연결
                    socket = new Socket(SERVER_IP, SERVER_PORT);
                    // 입력 스트림 설정
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    // 서버로부터 데이터를 읽어오는 루프
                    while (!Thread.currentThread().isInterrupted()) {
                        String data = bufferedReader.readLine();
                        if (data == null) {
                            // 데이터를 더 이상 읽을 수 없을 때 스레드 종료
                            break;
                        }
                        // 핸들러를 통해 메인 스레드로 데이터 전달
                        handler.obtainMessage(0, data).sendToTarget();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    // 소켓 연결 오류 처리
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                        }
                    });
                } finally {
                    try {
                        // 소켓 및 입력 스트림 닫기
                        if (bufferedReader != null) bufferedReader.close();
                        if (socket != null) socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
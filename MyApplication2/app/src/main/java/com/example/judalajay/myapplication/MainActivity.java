package com.example.judalajay.myapplication;


import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.net.URI;
import java.net.URISyntaxException;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

public class MainActivity extends AppCompatActivity {


    private final WebSocketConnection mWebSocketClient = new WebSocketConnection();

    TextView txtDislpay,txtSuccess;

    Button btnConnect,btnClose;
    String ip,userId;
    EditText editIp,editUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editIp= (EditText) findViewById(R.id.editIp);
        editUserId= (EditText) findViewById(R.id.editUserId);
        txtDislpay = (TextView) findViewById(R.id.tvDisplay);
        txtSuccess= (TextView) findViewById(R.id.txtSuccess);
        btnConnect= (Button) findViewById(R.id.btnConnect);
        btnClose= (Button) findViewById(R.id.btnClose);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editIp.getText()!=null && editUserId.getText()!=null) {

                    ip=editIp.getText().toString();
                    userId=editUserId.getText().toString();
                    connectWebSocket(ip,userId);
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });
    }

    private void close()
    {
        txtSuccess.setText("Connection Closed");
        Log.d("Connection ", "Connection Closed");
        mWebSocketClient.disconnect();
    }

    private void connectWebSocket(String ip,String userId) {

        final String uri = "ws://" + ip + "/socket/" + userId;
        try {

            mWebSocketClient.connect(uri, new WebSocketHandler() {
                @Override
                public void onOpen() {
                    txtSuccess.setText("Connection Open");
                    Log.d("Connection ", "Connection Open");
                }

                @Override
                public void onTextMessage(String message) {

                    switch (message)
                    {
                        case "0":
                                play(message,"dot");
                        case "1":
                            play(message,"one");
                        case "2":
                            play(message,"two");
                        case "3":
                            play(message,"three");
                        case "4":
                            play(message,"four");
                        case "5":
                            play(message,"five");
                        case "6":
                            play(message,"six");
                        case "No Ball":
                            play(message,"nb");
                        case "Wide Ball":
                            play(message,"wide");
                        case "Wicket":
                            play(message,"w");
                        case "Third Empire":
                            play(message,"tempire");
                        case "Dot Ball":
                            play(message,"dot");
                        case "Free Hit":
                            play(message,"free");
                        case "Havame":
                            play(message,"havame");
                        case "Over":
                            play(message,"over");
                        case "Spinner Aaya":
                            play(message,"spinner");
                        case "Sorry":
                            play(message,"sorry");
                    }


                }
                public void play(String message,String fname) {

                    TextView textView = (TextView) findViewById(R.id.tvDisplay);
                    textView.setText("");
                    textView.setText(textView.getText() + "" + message);

                    int resID=getResources().getIdentifier(fname, "raw", getPackageName());

                    MediaPlayer mediaPlayer=MediaPlayer.create(MainActivity.this,resID);
                    mediaPlayer.start();

                }

                @Override
                public void onClose(int i, String s) {
                    Log.i("Websocket", "Closed " + s);
                }
            });
        }
     catch (WebSocketException e) {

     e.printStackTrace();
    }
        mWebSocketClient.disconnect();
    }


}
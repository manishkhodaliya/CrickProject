package com.example.judalajay.myapplication;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    WebSocketClient mWebSocketClient;
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



    }

    private void close()
    {
        mWebSocketClient.close();
    }

    private void connectWebSocket(String ip,String userId) {
        URI uri;

        try {
            uri = new URI("ws://"+ip+"/socket/"+userId);

        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }


        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                txtSuccess.setText("Connection Open");
                Log.d("open","opnconnction");
             }

            @Override
            public void onMessage(String s) {
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView textView = (TextView)findViewById(R.id.tvDisplay);
                        textView.setText("");
                        textView.setText(textView.getText() + "\n" + message);
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }


}
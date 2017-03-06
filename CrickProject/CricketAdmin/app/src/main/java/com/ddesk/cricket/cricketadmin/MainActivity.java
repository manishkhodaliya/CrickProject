package com.ddesk.cricket.cricketadmin;

import android.content.DialogInterface;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    WebSocketClient mWebSocketClient;
    TextView txtDislpay,txtSuccess;

    private static final String szero="S-0",sone="S-1",stwo="S-2",sthree="S-3",sfour="S-4",sfive="S-5",ssix="S-6",snb="S-No Ball",swb="S-Wide Ball",sw="S-Wicket",
    stempire="S-Third Empire",sblank="S-Dot Ball",sfree="S-Free Hit",shavame="S-Havame",sover="S-Over",sspinner="S-Spinner Aaya",ssorry="S-Sorry";

    Boolean isOpen=false;
    Button btnConnect,btnClose;
    Button zero,one,two,three,four,five,six,nb,wd,w,tempire,blank,free,havame,over,spinner,sorry;
    String ip,userId;
    EditText editIp,editUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editIp= (EditText) findViewById(R.id.editIp);
        editUserId= (EditText) findViewById(R.id.editUserId);

        txtSuccess= (TextView) findViewById(R.id.txtSuccess);
        btnConnect= (Button) findViewById(R.id.btnConnect);
        btnClose= (Button) findViewById(R.id.btnClose);

        zero= (Button) findViewById(R.id.zero);
        one= (Button) findViewById(R.id.one);
        two= (Button) findViewById(R.id.two);
        three= (Button) findViewById(R.id.three);
        four= (Button) findViewById(R.id.four);
        five= (Button) findViewById(R.id.five);
        six= (Button) findViewById(R.id.six);
        nb= (Button) findViewById(R.id.nb);
        wd= (Button) findViewById(R.id.wb);
        w= (Button) findViewById(R.id.w);
        tempire= (Button) findViewById(R.id.tempire);
        blank= (Button) findViewById(R.id.blank);
        free= (Button) findViewById(R.id.free);
        havame= (Button) findViewById(R.id.havame);
        over= (Button) findViewById(R.id.over);
        spinner= (Button) findViewById(R.id.spinner);
        sorry= (Button) findViewById(R.id.sorry);


        btnConnect.setOnClickListener(this);
        zero.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        w.setOnClickListener(this);
        wd.setOnClickListener(this);
        nb.setOnClickListener(this);
        tempire.setOnClickListener(this);
        blank.setOnClickListener(this);
        free.setOnClickListener(this);
        havame.setOnClickListener(this);
        over.setOnClickListener(this);
        spinner.setOnClickListener(this);
        sorry.setOnClickListener(this);
        btnClose.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.btnConnect) {
            if (editIp.getText() != null && editUserId.getText() != null) {
                Log.d("Pressed","Connect");
                ip = editIp.getText().toString();
                userId = editUserId.getText().toString();
                connectWebSocket(ip, userId);
            }
        }
        else if(v.getId()==R.id.btnClose)
        {
            mWebSocketClient.close();
        }

        else {
            switch (v.getId()) {
                case R.id.zero:
                    Log.d("Pressed", "Zero");
                    mWebSocketClient.send(szero);
                    break;
                case R.id.one:
                    Log.d("Pressed", "Zero");
                    mWebSocketClient.send(sone);
                    break;
                case R.id.two:
                    mWebSocketClient.send(stwo);
                    break;
                case R.id.three:
                    mWebSocketClient.send(sthree);
                    break;
                case R.id.four:
                    mWebSocketClient.send(sfour);
                    break;
                case R.id.five:
                    mWebSocketClient.send(sfive);
                    break;
                case R.id.six:
                    mWebSocketClient.send(ssix);
                    break;
                case R.id.w:
                    mWebSocketClient.send(sw);
                    break;
                case R.id.wb:
                    mWebSocketClient.send(swb);
                    break;
                case R.id.nb:
                    mWebSocketClient.send(snb);
                    break;
                case R.id.free:
                    mWebSocketClient.send(sfree);
                    break;
                case R.id.blank:
                    mWebSocketClient.send(sblank);
                    break;
                case R.id.havame:
                    mWebSocketClient.send(shavame);
                    break;
                case R.id.tempire:
                    mWebSocketClient.send(stempire);
                    break;
                case R.id.over:
                    mWebSocketClient.send(sover);
                    break;
                case R.id.spinner:
                    mWebSocketClient.send(sspinner);
                    break;
                case R.id.sorry:
                    mWebSocketClient.send(ssorry);
                    break;
            }

        }


    }
    private void close()
    {
        txtSuccess.setText("Connection Close");
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
                isOpen=true;
                Log.d("open", "opnconnction");
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

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
package com.example.blackhat.mlive.activity;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.blackhat.mlive.R;

public class ChageEventActivity extends AppCompatActivity {

   SeekBar seekBar;
    TextView textView,tv1,tv2,tv3,tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chage_event);

         seekBar= (SeekBar) findViewById(R.id.seekBar);
        textView= (TextView) findViewById(R.id.tv);
        tv1= (TextView) findViewById(R.id.tv1);
        tv2= (TextView) findViewById(R.id.tv2);
        tv3= (TextView) findViewById(R.id.tv3);
        tv4= (TextView) findViewById(R.id.tv4);
        seekBar.setMax(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                progress=progress+10;
                System.out.println("Progreass= "+progress);

                /*textView.setTextSize(progress);
                tv1.setTextSize(progress);
                tv2.setTextSize(progress);
                tv3.setTextSize(progress);
                tv4.setTextSize(progress);*/
                


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

               // seekBar.setProgress(process);



            }




        });


    }
}

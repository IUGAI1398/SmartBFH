package com.example.smartt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class FarmSettings extends AppCompatActivity {
    SwitchCompat aswitchled,aswitchwaterpump, aswitchfan;
    NumberPicker numberPicker1, numberPicker2;
    SeekBar progressBar1, progressBar2;
    Button button;
    String seekbar1value = "20", seekbar2value = "65";
    TextView textView1, textView2;
    int startpick = 0,endpick = 0;
    String switchledvalue = "0",aswtichlwaterpumpvalue = "0",aswitchfanvalue = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_settings);
        aswitchled = findViewById(R.id.switch1);
        aswitchwaterpump = findViewById(R.id.switch2);
        numberPicker2 = findViewById(R.id.numberpickerend);
        numberPicker1 = findViewById(R.id.numberpickerstart);
        aswitchfan = findViewById(R.id.switch3);
        numberPicker1.setMinValue(0);
        numberPicker1.setMaxValue(24);
        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(24);
        progressBar1 = findViewById(R.id.progress);
        textView1 = findViewById(R.id.textview1);
        progressBar2 = findViewById(R.id.progress2);
        textView2 = findViewById(R.id.textview_humidity);
        button = findViewById(R.id.buttonid_send);
        progressBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                  textView1.setText(String.format("%d",i));
                  seekbar1value  = String.format("%d",i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        progressBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView2.setText(String.format("%d",i));
                seekbar2value = String.format("%d",i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                startpick = numberPicker.getValue();
            }
        });
        numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                endpick = numberPicker.getValue();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InserttodbFarmValues inserttodbFarmValues = new InserttodbFarmValues();
                inserttodbFarmValues.execute(seekbar1value,seekbar2value,String.format("%d",startpick),String.format("%d",endpick));
            }
        });
        aswitchled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheked) {
                if (isCheked){
                    switchledvalue = "1";
                } else {
                    switchledvalue = "0";
                }
            }
        });
        aswitchwaterpump.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheked) {
                if (isCheked){
                    aswtichlwaterpumpvalue = "1";
                } else {
                    aswtichlwaterpumpvalue = "0";
                }
            }
        });
        aswitchfan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheked ){
                if (isCheked){
                    aswitchfanvalue = "1";
                } else {
                    aswitchfanvalue = "0";
                }
            }
        });
    }


    public void changbutton(View view) {
        InserttodbFarmSettings inserttodbfan = new InserttodbFarmSettings();
        inserttodbfan.execute(switchledvalue,aswtichlwaterpumpvalue,aswitchfanvalue);
    }
}
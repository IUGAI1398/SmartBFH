package com.example.smartt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class GetSensors extends AppCompatActivity {
    String url = "http://iugaialeksandr.dothome.co.kr/api/readone.php";
    String datas;
    ArrayList<SensorsValue> arrayList;
    RecyclerView recyclerView;
    String settingtemp = "20",settinghum="65",settingfood="1";
    int valueofwater, valueofdistance;
    ProgressBar progressBar, progressBar0;
    SeekBar seekBar1, seekBar2;
    RadioGroup radiogroup;
    AsyncTaskClass asyncTaskClass;
    Button buttonsend;
    ImageView home;
    Adapter adapter;
    SwitchCompat aswitch;
    TextView textView, textView1,textView2,textView3,textviewhum,textView4,textView5;
    Handler handler = new Handler();
    String temperature,humidity, temperature_text;
    String aswitchvalue = "0";
    double temperature_fl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_sensors);
        seekBar1 = findViewById(R.id.progress);
        seekBar2 = findViewById(R.id.progress2);
        textView = findViewById(R.id.textview_temp);
        textView1 = findViewById(R.id.textoftemp);
        buttonsend = findViewById(R.id.buttonid_send);
        progressBar = findViewById(R.id.progress_bar1);
        textView4 = findViewById(R.id.temperatureprogress);
        textView5 = findViewById(R.id.humidityprogress);
        radiogroup = findViewById(R.id.radio);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio1:
                        Toast.makeText(GetSensors.this, "3", Toast.LENGTH_SHORT).show();
                        settingfood = "3";
                        break;
                    case R.id.radio2:
                        Toast.makeText(GetSensors.this, "2", Toast.LENGTH_SHORT).show();
                        settingfood = "2";
                        break;
                    case R.id.radio3:
                        Toast.makeText(GetSensors.this, "1", Toast.LENGTH_SHORT).show();
                        settingfood = "1";
                        break;
                }
            }
        });
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView4.setText(String.format("%d℃",progress));
                settingtemp = String.format("%d", progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView5.setText(String.format("%d",progress));
                settinghum = String.format("%d", progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        progressBar0 = findViewById(R.id.progress_bar0);
        textView2 = findViewById(R.id.valueofwater);
        textView3 = findViewById(R.id.valueofdistance);
        textviewhum = findViewById(R.id.textview_humidity);
        buttonsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InserttoDb inserttoDb = new InserttoDb();
                int settingho = Integer.parseInt(settingtemp);
                if (settingho > 26){
                    aswitch.setChecked(true);
                    aswitchvalue = "1";
                } else {
                    aswitchvalue = "0";
                    aswitch.setChecked(false);
                }
                inserttoDb.execute(settingtemp,settinghum,settingfood);
                Inserttodbfan inserttodbfan = new Inserttodbfan();
                inserttodbfan.execute(aswitchvalue);
            }
        });
        aswitch = findViewById(R.id.switch1);
        aswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                      aswitchvalue = "1";
                } else {
                      aswitchvalue = "0";
                }
            }
        });
        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetSensors.this, MainActivity.class);
                startActivity(intent);
            }
        });
       content();
    }

    public void content() {
        asyncTaskClass = new AsyncTaskClass(textView);
        try {
            datas = asyncTaskClass.execute(url).get();
            JsonUnits jsonUnits = new JsonUnits();
            arrayList = jsonUnits.getdatas(datas);
            for (int i = 0; i < arrayList.size(); i++) {
                temperature = arrayList.get(i).getTemperature();
                humidity = arrayList.get(i).getHumidity();
                valueofwater = Integer.parseInt(arrayList.get(i).getWater());
                valueofdistance = Integer.parseInt(arrayList.get(i).getDistance());
                progressBar.setProgress(valueofwater);
                progressBar0.setProgress(valueofdistance);
                int percentege = valueofwater / 10;
                if (0 <= valueofwater && valueofwater <= 400){
                    progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbarred));
                } else if (400 < valueofwater && valueofwater < 800){
                    progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbaryellow));
                } else {
                    progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbargreen));
                }
                if (0 <= valueofdistance && valueofdistance <= 10 ){
                    progressBar0.setProgressDrawable(getResources().getDrawable(R.drawable.progressbargreen));
                } else if (10 < valueofdistance && valueofdistance <= 20){
                    progressBar0.setProgressDrawable(getResources().getDrawable(R.drawable.progressbaryellow));
                } else if (20 <= valueofdistance) {
                    progressBar0.setProgressDrawable(getResources().getDrawable(R.drawable.progressbarred));
                }
                textView2.setText(percentege + "%");
                textView3.setText(arrayList.get(i).getDistance());
                textView.setText("온도 : " + temperature + "℃");
                textviewhum.setText("습도 : " + humidity + "%");
                temperature_fl = Double.parseDouble(temperature);
                if (temperature_fl >= 30) {
                    temperature_text = "아주 더워요";
                } else if (temperature_fl < 30 || temperature_fl >= 20) {
                    temperature_text = "정상 온도";
                } else {
                    temperature_text = "주워요";
                }
                textView1.setText(temperature_text);

            }

//            adapter = new Adapter(arrayList);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//            recyclerView.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        refresh(1000);
    }

    public  void refresh(int miliseconds){
         final  Runnable runnable = new Runnable() {
             @Override
             public void run() {
                 content();
             }
         };
         handler.postDelayed(runnable,miliseconds);
    }

    public void switch_on_button(View view) {
        Inserttodbfan inserttodbfan = new Inserttodbfan();
        inserttodbfan.execute(aswitchvalue);
    }
}
package com.example.smartt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SmartFarm extends AppCompatActivity {
    ImageView imageViewback;
    Handler handler = new Handler();
    TextView textViewtemperature, textViewhumidity,waterlevel, soillevel;
    ArrayList<SmartFarmValues> arrayList = new ArrayList<>();
    String water,soild,gas,temperatureget,humidityget;
    String datas;
    String url = "http://iugaialeksandr.dothome.co.kr/apifarm/readonefarm.php";
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_farm);
        imageViewback = findViewById(R.id.home);
        button = findViewById(R.id.buttongo);
        textViewtemperature  = findViewById(R.id.textviewtemperature);
        textViewhumidity = findViewById(R.id.textviewhumidity);
        waterlevel = findViewById(R.id.textviewwater);



        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SmartFarm.this,FarmSettings.class);
                startActivity(intent);
            }
        });
    }

    public void content(){
        AsyncTaskClass asyncTaskClass = new AsyncTaskClass(textViewhumidity);
        try {
            datas = asyncTaskClass.execute(url).get();
            JsonUnits jsonUnits = new JsonUnits();
            arrayList =  jsonUnits.getdatas2(datas);
            for (int i = 0; i < arrayList.size();i++){
//                textViewhumidity.setText(arrayList.get(i).getTemperature());
//                textViewtemperature.setText(arrayList.get(i).getHumidity());
                temperatureget = arrayList.get(i).getTemperature();
                humidityget = arrayList.get(i).getHumidity();
                water = arrayList.get(i).getWater();
                waterlevel.setText(water);
                soild = arrayList.get(i).getSolid();
                gas = arrayList.get(i).getCo2Gas();
                textViewtemperature.setText(temperatureget);
                textViewhumidity.setText(humidityget);
                waterlevel.setText(water);
                Log.i("ASdsad", temperatureget + humidityget + water);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
}
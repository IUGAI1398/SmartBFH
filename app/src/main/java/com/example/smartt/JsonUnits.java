package com.example.smartt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUnits {

   public  ArrayList<SensorsValue> getdatas(String json) throws JSONException {
       ArrayList<SensorsValue> arrayList = new ArrayList<>();
       ArrayList<SmartFarmValues> arrayList2 = new ArrayList<>();
       String temperature1 = null;
       String humidity1 = null;
       String water1 = null;
       String distance1 = null;
       try {
           JSONArray jsonArray = new JSONArray(json);
           for (int i = 0; i < jsonArray.length();i++){
               JSONObject jsonObject = jsonArray.getJSONObject(i);
               float temperature = (float) jsonObject.getDouble("Temprature");
               float humidity = (float) jsonObject.getDouble("Humidity");
               int water = jsonObject.getInt("Water");
               int distanse = jsonObject.getInt("Distance");
               int co2gas = jsonObject.getInt("Co2Gas");
               String soillelvel = String.format("%d",jsonObject.getInt("Soillevel"));
               String co2gasstring = String.format("%d",co2gas);
               temperature1  = String.format("%.1f", temperature);
               humidity1 = String.format("%.1f ", humidity);
               water1 = String.format("%d", water);
               distance1 = String.format("%d", distanse);
               arrayList.add(new SensorsValue(temperature1,humidity1,water1,distance1));//
           }
       } catch (JSONException e) {
           e.printStackTrace();
       }
       return  arrayList;
   }

    public  ArrayList<SmartFarmValues> getdatas2(String json) throws JSONException {
        ArrayList<SensorsValue> arrayList = new ArrayList<>();
        ArrayList<SmartFarmValues> arrayList2 = new ArrayList<>();
        String temperaturefarm = null;
        String humidityfarm = null;
        String water1 = null;
        String distance1 = null;
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                float temperature = (float) jsonObject.getDouble("Temprature");
                float humidity = (float) jsonObject.getDouble("Humidity");
                int water = jsonObject.getInt("Waterlevel");
                int soillevel = jsonObject.getInt("Soillevel");
                int co2gas = jsonObject.getInt("Co2Gas");
                String soillelvel = String.format("%d",soillevel);
                String co2gasstring = String.format("%d",co2gas);
                temperaturefarm = String.format("%.1f", temperature);
                humidityfarm = String.format("%.1f ", humidity);
                water1 = String.format("%d", water);
                arrayList2.add(new SmartFarmValues(temperaturefarm,humidityfarm,water1,soillelvel,co2gasstring));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  arrayList2;
    }

}

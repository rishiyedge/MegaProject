package com.example.smartirrigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.smartirrigation.data.ReadingData;
import com.example.smartirrigation.utils.AppConstants;
import com.example.smartirrigation.utils.LinearRegression;
import com.example.smartirrigation.utils.ResponseData;
import com.example.smartirrigation.volley.JsonRequest;
import com.example.smartirrigation.volley.VolleySingleton;
import com.github.anastr.speedviewlib.SpeedView;
import com.github.anastr.speedviewlib.TubeSpeedometer;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SpeedView tubeSpeedometer1;
    TextView txtvalue,lbl,txtpredvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tubeSpeedometer1 = findViewById(R.id.speed111);
        txtvalue = findViewById(R.id.txtvalue);
        lbl = findViewById(R.id.txtlbl);
        txtpredvalue=findViewById(R.id.txtpredvalue);

        final String  key = (String) getIntent().getSerializableExtra("key");

        loadItemListData(key);
        loadPredictionData(key);
    }
    public void showlist(View v)
    {
        Intent intent = new Intent(getApplicationContext(), ReadingListActivity.class);

        startActivity(intent);
    }
    private void loadPredictionData(String key)
    {
        JsonRequest ViewDataRequest = new JsonRequest(
                Request.Method.GET,
                AppConstants.READING_HISTORY_DATA, null,
                response -> {
                    Gson gson = new Gson();
                    ResponseData responseData = gson.fromJson(response.toString(), ResponseData.class);

                    //delayedProgressDialog.cancel();
                    if (responseData.getMessage().equals(AppConstants.RESPONSE_SUCCESS)) {
                        if (responseData.getDataList() != null && responseData.getDataList().size() > 0) {
                            List<ReadingData> lst = responseData.getDataList();
                            List<Long> times = new ArrayList<>();
                            List<Double> values = new ArrayList<>();

                            long lastms = 0;
                            for (ReadingData d : lst) {
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.DATE, Integer.parseInt(d.getDd()));
                                c.set(Calendar.MONTH, Integer.parseInt(d.getMm()));
                                c.set(Calendar.YEAR, Integer.parseInt(d.getYy()));
                                long ms = c.getTimeInMillis();
                                times.add(ms);
                                lastms = ms;
                            }
                            long dependantvar = lastms + (24 * 60 * 60 * 1000);
                            if (key.equals("Moisture")) {
                                for (ReadingData d : lst) {
                                    values.add(Double.parseDouble(d.getSoilvalue()));

                                }

                                LinearRegression lr = new LinearRegression(times, values);
                                double result = lr.predict((double) dependantvar);
                                txtpredvalue.setText(String.format("%.2f", result) + " (% MP)");
                            } else if (key.equals("Temp")) {
                                for (ReadingData d : lst) {
                                    values.add(Double.parseDouble(d.getTempvalue()));

                                }

                                LinearRegression lr = new LinearRegression(times, values);
                                double result = lr.predict((double) dependantvar);
                                txtpredvalue.setText(String.format("%.2f", result) + " (°C)");
                            } else if (key.equals("Humidity")) {
                                for (ReadingData d : lst) {
                                    values.add(Double.parseDouble(d.getHumidityvalue()));

                                }

                                LinearRegression lr = new LinearRegression(times, values);
                                double result = lr.predict((double) dependantvar);
                                txtpredvalue.setText(String.format("%.2f", result) + " (% RH)");
                            } else if (key.equals("Rain")) {
                                for (ReadingData d : lst) {
                                    values.add(Double.parseDouble(d.getRainvalue()));

                                }

                                LinearRegression lr = new LinearRegression(times, values);
                                double result = lr.predict((double) dependantvar);
                                txtpredvalue.setText(String.format("%.2f", result) + " (T/F)");
                            }


                        } else {
                            Toast.makeText(MainActivity.this, responseData.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                error -> {
                    //delayedProgressDialog.cancel();
                    Toast.makeText(MainActivity.this, VolleySingleton.getErrorMessage(error), Toast.LENGTH_LONG).show();
                });
        ViewDataRequest.setRetryPolicy(VolleySingleton.getDefaultRetryPolice());
        ViewDataRequest.setShouldCache(false);
        VolleySingleton.getInstance().addToRequestQueue(ViewDataRequest, "ViewDataRequest");
    }
    private void loadItemListData(String key) {


        JsonRequest ViewDataRequest = new JsonRequest(
                Request.Method.GET,
                AppConstants.READING_DATA, null,
                response -> {
                    Gson gson = new Gson();
                    ResponseData responseData = gson.fromJson(response.toString(), ResponseData.class);
                    //delayedProgressDialog.cancel();
                    if (responseData.getMessage().equals(AppConstants.RESPONSE_SUCCESS)) {

                        if(key.equals("Moisture")) {
                            lbl.setText("Soil Moisture");
                            tubeSpeedometer1.setSpeedTextSize(70);
                            tubeSpeedometer1.setMinSpeed(0);
                            tubeSpeedometer1.setMaxSpeed(100f);
                            tubeSpeedometer1.setSpeedAt(Float.parseFloat(responseData.getSoilvalue()));
                            txtvalue.setText(responseData.getSoilvalue() +" (% MP)");
                            // tubeSpeedometer1.setCenterCircleColor(R.color.red);
                        }
                        else if(key.equals("Temp")) {
                            lbl.setText("Temperature");
                            tubeSpeedometer1.setSpeedTextSize(70);
                            tubeSpeedometer1.setMinSpeed(20);
                            tubeSpeedometer1.setMaxSpeed(50f);
                            tubeSpeedometer1.setSpeedAt(Float.parseFloat(responseData.getTempvalue()));
                            txtvalue.setText(responseData.getTempvalue() + " (°C)");
                            // tubeSpeedometer1.setCenterCircleColor(R.color.red);
                        }
                        else if(key.equals("Humidity")) {
                            lbl.setText("Humidity");
                            tubeSpeedometer1.setSpeedTextSize(70);
                            tubeSpeedometer1.setMinSpeed(50);
                            tubeSpeedometer1.setMaxSpeed(100f);
                            tubeSpeedometer1.setSpeedAt(Float.parseFloat(responseData.getHumidityvalue()));
                            txtvalue.setText(responseData.getHumidityvalue() +" (% RH)");
                            // tubeSpeedometer1.setCenterCircleColor(R.color.red);
                        }
                        else if(key.equals("Rain")) {
                            lbl.setText("Rain");
                            tubeSpeedometer1.setSpeedTextSize(70);
                            tubeSpeedometer1.setMinSpeed(0);
                            tubeSpeedometer1.setMaxSpeed(1f);
                            tubeSpeedometer1.setSpeedAt(Float.parseFloat(responseData.getRainvalue()));
                            txtvalue.setText(responseData.getRainvalue() +" (T/F)");
                            // tubeSpeedometer1.setCenterCircleColor(R.color.red);
                        }


                    } else {
                        Toast.makeText(MainActivity.this, responseData.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    //delayedProgressDialog.cancel();
                    Toast.makeText(MainActivity.this, VolleySingleton.getErrorMessage(error), Toast.LENGTH_LONG).show();
                });
        ViewDataRequest.setRetryPolicy(VolleySingleton.getDefaultRetryPolice());
        ViewDataRequest.setShouldCache(false);
        VolleySingleton.getInstance().addToRequestQueue(ViewDataRequest, "ViewDataRequest");
    }
}
package com.example.smartirrigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.smartirrigation.utils.AppConstants;
import com.example.smartirrigation.utils.ResponseData;
import com.example.smartirrigation.volley.JsonRequest;
import com.example.smartirrigation.volley.VolleySingleton;
import com.google.gson.Gson;

public class WaterPumpActivity extends AppCompatActivity {

    TextView txtonvalue,lbl,txtoffvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_pump);

        txtonvalue = findViewById(R.id.txtOnTime);
        //lbl = findViewById(R.id.txtlbl);
        txtoffvalue=findViewById(R.id.txtOffTime);

        JsonRequest ViewDataRequest = new JsonRequest(
                Request.Method.GET,
                AppConstants.READING_Waterpump_DATA, null,
                response -> {
                    Gson gson = new Gson();
                    ResponseData responseData = gson.fromJson(response.toString(), ResponseData.class);
                    //delayedProgressDialog.cancel();
                    if (responseData.getMessage().equals(AppConstants.RESPONSE_SUCCESS)) {
                        txtonvalue.setText("On Time :"+responseData.getHh1()+":"+responseData.getMi1());
                        txtoffvalue.setText("Off Time :"+responseData.getHh()+":"+responseData.getMi());
                    } else {
                        Toast.makeText(WaterPumpActivity.this, responseData.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    //delayedProgressDialog.cancel();
                    Toast.makeText(WaterPumpActivity.this, VolleySingleton.getErrorMessage(error), Toast.LENGTH_LONG).show();
                });
        ViewDataRequest.setRetryPolicy(VolleySingleton.getDefaultRetryPolice());
        ViewDataRequest.setShouldCache(false);
        VolleySingleton.getInstance().addToRequestQueue(ViewDataRequest, "ViewDataRequest");
    }
}
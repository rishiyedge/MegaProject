package com.example.smartirrigation;

import android.app.Application;
import android.os.Environment;
import android.util.Log;


import com.example.smartirrigation.volley.VolleySingleton;

import java.io.File;

public class SmartIrrigationApp extends Application {
    public static final String TAG = SmartIrrigationApp.class.getSimpleName();

    public static String APP_VERSION = "0.1";
    public static String ANDROID_ID = "0000000000000000";



    private static SmartIrrigationApp mSmartIrrigationAppContext;


    public static SmartIrrigationApp getSmartIrrigationAppContext() {
        return mSmartIrrigationAppContext;
    }

    public static void setSmartIrrigationApp(SmartIrrigationApp mSmartIrrigationAppContext) {
        SmartIrrigationApp.mSmartIrrigationAppContext = mSmartIrrigationAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        VolleySingleton.createInstance(getApplicationContext());

        SmartIrrigationApp.setSmartIrrigationApp(this);
        File yourAppStorageDir = new File(Environment.getExternalStorageDirectory(), "/" + getResources().getString(R.string.app_name) + "/");
        if (!yourAppStorageDir.exists()) {
            boolean isDirCreated = yourAppStorageDir.mkdirs();
            Log.d(TAG, "App mediaStorageDirectory created :" + isDirCreated);
        }
    }
}

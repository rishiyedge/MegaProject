package com.example.smartirrigation.utils;

public class AppConstants {


    public static String RESPONSE_SUCCESS ="Success";
    public static String RESPONSE_FAIL ="Fail";


    ///urls
    private final static String BASE_URL = "https://smartirregation.000webhostapp.com";
    public final static String READING_DATA = BASE_URL.concat("/getdata.php");

    public final static String READING_Waterpump_DATA = BASE_URL.concat("/getwaterpumpdata.php");


    public final static String READING_HISTORY_DATA = BASE_URL.concat("/getalldata.php");

}

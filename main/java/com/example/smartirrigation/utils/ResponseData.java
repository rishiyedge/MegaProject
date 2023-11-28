package com.example.smartirrigation.utils;


import com.example.smartirrigation.data.ReadingData;

import java.util.List;

/**
 * Created by rajkarekar on 02/26/22.
 */

public class ResponseData {

    private String Message;
    private String soilvalue,tempvalue,humidityvalue,rainvalue;
    private List<ReadingData> DataList;

    int dd,mm,yy,hh,mi,dd1,mm1,yy1,hh1,mi1;

    public int getDd() {
        return dd;
    }

    public void setDd(int dd) {
        this.dd = dd;
    }

    public int getMm() {
        return mm;
    }

    public void setMm(int mm) {
        this.mm = mm;
    }

    public int getYy() {
        return yy;
    }

    public void setYy(int yy) {
        this.yy = yy;
    }

    public int getHh() {
        return hh;
    }

    public void setHh(int hh) {
        this.hh = hh;
    }

    public int getMi() {
        return mi;
    }

    public void setMi(int mi) {
        this.mi = mi;
    }

    public int getDd1() {
        return dd1;
    }

    public void setDd1(int dd1) {
        this.dd1 = dd1;
    }

    public int getMm1() {
        return mm1;
    }

    public void setMm1(int mm1) {
        this.mm1 = mm1;
    }

    public int getYy1() {
        return yy1;
    }

    public void setYy1(int yy1) {
        this.yy1 = yy1;
    }

    public int getHh1() {
        return hh1;
    }

    public void setHh1(int hh1) {
        this.hh1 = hh1;
    }

    public int getMi1() {
        return mi1;
    }

    public void setMi1(int mi1) {
        this.mi1 = mi1;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;

    public String getSoilvalue() {
        return soilvalue;
    }

    public void setSoilvalue(String soilvalue) {
        this.soilvalue = soilvalue;
    }

    public String getTempvalue() {
        return tempvalue;
    }

    public void setTempvalue(String tempvalue) {
        this.tempvalue = tempvalue;
    }

    public String getHumidityvalue() {
        return humidityvalue;
    }

    public void setHumidityvalue(String humidityvalue) {
        this.humidityvalue = humidityvalue;
    }

    public String getRainvalue() {
        return rainvalue;
    }

    public void setRainvalue(String rainvalue) {
        this.rainvalue = rainvalue;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<ReadingData> getDataList() {
        return DataList;
    }

    public void setDataList(List<ReadingData> DataList) {
        this.DataList = DataList;
    }
















}

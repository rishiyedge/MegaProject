package com.example.smartirrigation.data;

import java.io.Serializable;

public class ReadingData implements Serializable {

    private String soilvalue,tempvalue,humidityvalue,rainvalue;
    private String id,dd,mm,yy;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public String getYy() {
        return yy;
    }

    public void setYy(String yy) {
        this.yy = yy;
    }
}


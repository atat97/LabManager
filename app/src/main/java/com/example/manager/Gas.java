package com.example.manager;

public class Gas {
    String name;
    String value;
    String location;
    String user;
    String acq_date;    //Acquired date
    String ret_date;    //Returned date

    public Gas(){}

    public Gas(String name, String amount, String location, String acq_date, String ret_date) {
        this.name = name;
        this.value = amount;
        this.location = location;
        this.acq_date = acq_date;
        this.ret_date = ret_date;
    }

    //getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUser() {
        return user;
    }

    //setters
    public void setUser(String user) {
        this.user = user;
    }

    public String getAcq_date() {
        return acq_date;
    }

    public void setAcq_date(String acq_date) {
        this.acq_date = acq_date;
    }

    public String getRet_date() {
        return ret_date;
    }

    public void setRet_date(String ret_date) {
        this.ret_date = ret_date;
    }
}

package com.example.manager;

import android.os.Parcel;
import android.os.Parcelable;

public class Gas implements Parcelable {
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

    protected Gas(Parcel in) {
        name = in.readString();
        value = in.readString();
        location = in.readString();
        user = in.readString();
        acq_date = in.readString();
        ret_date = in.readString();
    }

    public static final Creator<Gas> CREATOR = new Creator<Gas>() {
        @Override
        public Gas createFromParcel(Parcel in) {
            return new Gas(in);
        }

        @Override
        public Gas[] newArray(int size) {
            return new Gas[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getLocation() {
        return location;
    }

    public String getUser() {
        return user;
    }

    public String getAcq_date() {
        return acq_date;
    }

    public String getRet_date() {
        return ret_date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setAcq_date(String acq_date) {
        this.acq_date = acq_date;
    }

    public void setRet_date(String ret_date) {
        this.ret_date = ret_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(value);
        dest.writeString(location);
        dest.writeString(user);
        dest.writeString(acq_date);
        dest.writeString(ret_date);
    }
}

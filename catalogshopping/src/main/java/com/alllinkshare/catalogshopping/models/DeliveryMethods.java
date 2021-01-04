package com.alllinkshare.catalogshopping.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DeliveryMethods implements Parcelable {
    @SerializedName("ALS")
    private boolean isALSAvailable;

    @SerializedName("O2O")
    private boolean isO2OAvailable;

    @SerializedName("D2D")
    private boolean isD2DAvailable;

    @SerializedName("COD")
    private boolean isCODAvailable;

    public DeliveryMethods(boolean isALSAvailable, boolean isO2OAvailable, boolean isD2DAvailable, boolean isCODAvailable) {
        this.isALSAvailable = isALSAvailable;
        this.isO2OAvailable = isO2OAvailable;
        this.isD2DAvailable = isD2DAvailable;
        this.isCODAvailable = isCODAvailable;
    }

    protected DeliveryMethods(Parcel in) {
        isALSAvailable = in.readByte() != 0;
        isO2OAvailable = in.readByte() != 0;
        isD2DAvailable = in.readByte() != 0;
        isCODAvailable = in.readByte() != 0;
    }

    public static final Creator<DeliveryMethods> CREATOR = new Creator<DeliveryMethods>() {
        @Override
        public DeliveryMethods createFromParcel(Parcel in) {
            return new DeliveryMethods(in);
        }

        @Override
        public DeliveryMethods[] newArray(int size) {
            return new DeliveryMethods[size];
        }
    };

    public boolean isALSAvailable() {
        return isALSAvailable;
    }

    public boolean isO2OAvailable() {
        return isO2OAvailable;
    }

    public boolean isD2DAvailable() {
        return isD2DAvailable;
    }

    public boolean isCODAvailable() {
        return isCODAvailable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isALSAvailable ? 1 : 0));
        dest.writeByte((byte) (isO2OAvailable ? 1 : 0));
        dest.writeByte((byte) (isD2DAvailable ? 1 : 0));
        dest.writeByte((byte) (isCODAvailable ? 1 : 0));
    }
}
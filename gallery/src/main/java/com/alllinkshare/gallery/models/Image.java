package com.alllinkshare.gallery.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Image implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("source")
    private String source;

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }

    };

    public Image(int id, String source) {
        this.id = id;
        this.source = source;
    }

    public Image(Parcel in) {
        super();
        readFromParcel(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(source);
    }

    public void readFromParcel(Parcel in){
        id = in.readInt();
        source = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public String getSource() {
        return source;
    }
}
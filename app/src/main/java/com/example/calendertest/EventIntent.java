package com.example.calendertest;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.List;

public class EventIntent implements Parcelable {
    public List<Event> listevent;
    int one;
    long dayClickedMills;
    String content;
    EventIntent(int o,long d,String con){
        one=o;
        dayClickedMills=d;
        content=con;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(one);
        dest.writeLong(dayClickedMills);
        dest.writeString(content);
    }
    public static final Parcelable.Creator<EventIntent> CREATOR=new Parcelable.Creator<EventIntent>(){
        @Override
        public EventIntent createFromParcel(Parcel source) {
            EventIntent e=new EventIntent(source.readInt(),source.readLong(),source.readString());
            return e;
        }

        @Override
        public EventIntent[] newArray(int size) {
            return new EventIntent[size];
        }
    };
}


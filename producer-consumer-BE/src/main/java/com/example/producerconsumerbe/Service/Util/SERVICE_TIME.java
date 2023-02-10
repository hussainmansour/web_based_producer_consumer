package com.example.producerconsumerbe.Service.Util;

// in milliseconds
public enum SERVICE_TIME {
    MINIMUM(5000),MAXIMUM(15000);

    public final int time;
    SERVICE_TIME(int time){
        this.time = time;
    }
}

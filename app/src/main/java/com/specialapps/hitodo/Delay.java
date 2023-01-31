package com.specialapps.hitodo;
import android.os.Handler;

public class Delay {
    final long milli;

    public Delay(long milli){
        this.milli = milli;
    }

    public void then(Runnable runnable){
        Handler handler = new Handler();
        handler.postDelayed(runnable, this.milli);

    }
}

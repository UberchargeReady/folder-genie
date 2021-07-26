package com.dyejeekis.foldergenie.util;

import android.content.Context;
import android.widget.Toast;

public class DisplayToast implements Runnable {
    private final Context context;
    String text;
    int duration;

    public DisplayToast(Context context, String text, int duration){
        this.context = context;
        this.text = text;
        this.duration = duration;
    }

    public void run(){
        Toast.makeText(context, text, duration).show();
    }
}
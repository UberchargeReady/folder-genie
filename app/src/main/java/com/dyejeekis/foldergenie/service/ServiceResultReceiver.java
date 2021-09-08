package com.dyejeekis.foldergenie.service;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class ServiceResultReceiver extends ResultReceiver {

    public static final int CODE_SHOW_PROGRESS = 2000;
    public static final int CODE_OPERATION_COMPLETION = 2001;
    public static final int CODE_OPERATION_SUCCESS = 2002;

    public static final String KEY_PROGRESS_CURRENT = "key.PROGRESS_CURRENT";
    public static final String KEY_PROGRESS_MAX = "key.PROGRESS_MAX";
    public static final String KEY_PROGRESS_MESSAGE = "key.PROGRESS_MESSAGE";
    public static final String KEY_OPERATION_COMPLETED = "key.OPERATION_COMPLETED";
    public static final String KEY_OPERATION_SUCCESS = "key.OPERATION_SUCCESS";

    private Receiver receiver;

    public ServiceResultReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (receiver != null) {
            receiver.onReceiveResult(resultCode, resultData);
        }
    }

    public interface Receiver {
        void onReceiveResult(int resultCode, Bundle resultData);
    }
}

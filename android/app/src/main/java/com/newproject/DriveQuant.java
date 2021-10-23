package com.newproject;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.drivequant.drivekit.core.DriveKit;
import com.drivequant.drivekit.tripanalysis.DriveKitTripAnalysis;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class DriveQuant extends ReactContextBaseJavaModule {
private  static ReactApplicationContext reactContext;

    DriveQuant(ReactApplicationContext context){
        super(context);
        reactContext = context;
    }

    @ReactMethod
    public void init()  {
        DriveKit.INSTANCE.initialize(getCurrentActivity().getApplication());
        DriveKit.INSTANCE.setApiKey("NUpEDEzgmqfXu3KRaYvNA9xO");
    }
//
//         DriveKit.INSTANCE.setApiKey("NUpEDEzgmqfXu3KRaYvNA9xO");
//      DriveKit.INSTANCE.setUserId("juanisco7@gmail.com");


    @ReactMethod
    public void receiveEmail(String email)  {
        DriveKit.INSTANCE.setUserId(email);
    }


    @ReactMethod
    public void startTrip()  {
        DriveKitTripAnalysis.INSTANCE.startTrip();
    }


    @ReactMethod
    public void stopTrip() {
        DriveKitTripAnalysis.INSTANCE.stopTrip();
    }


    @ReactMethod
    public void isConfigured(Callback callBack) {
        boolean isConfigured = DriveKitTripAnalysis.INSTANCE.isConfigured();
        callBack.invoke(isConfigured);
    }

    //If you need to reset DriveKit configuration (user logout for example), you can call the following method:
    @ReactMethod
    public void reset() {
        DriveKit.INSTANCE.reset();
        DriveKitTripAnalysis.INSTANCE.reset();
    }


    @NonNull
    @Override
    public String getName() {
        return "DriveQuant";
    }


}

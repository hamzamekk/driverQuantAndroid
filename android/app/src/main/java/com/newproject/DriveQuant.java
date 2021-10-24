package com.newproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
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
    public void activateAutoStart(boolean state){
        DriveKitTripAnalysis.INSTANCE.activateAutoStart(state);
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
    public void checkBatteryOptimization(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            String packageName = getReactApplicationContext().getPackageName();
            PowerManager pm = (PowerManager) getReactApplicationContext().getSystemService(Context.POWER_SERVICE);
            if (pm != null && !pm.isIgnoringBatteryOptimizations(packageName)){
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                reactContext.startActivity(intent);
            }
        }
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

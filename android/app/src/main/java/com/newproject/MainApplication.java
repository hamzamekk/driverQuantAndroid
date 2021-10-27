package com.newproject;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.drivequant.drivekit.core.DriveKit;
import com.drivequant.drivekit.core.DriveKitSharedPreferencesUtils;
import com.drivequant.drivekit.tripanalysis.DriveKitTripAnalysis;
import com.drivequant.drivekit.tripanalysis.TripListener;
import com.drivequant.drivekit.tripanalysis.entity.TripNotification;
import com.drivequant.drivekit.tripanalysis.entity.TripPoint;
import com.drivequant.drivekit.tripanalysis.service.recorder.StartMode;
import com.drivequant.drivekit.tripanalysis.service.recorder.State;
import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.soloader.SoLoader;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {

  private final ReactNativeHost mReactNativeHost =
      new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
          return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
          @SuppressWarnings("UnnecessaryLocalVariable")
          List<ReactPackage> packages = new PackageList(this).getPackages();
          // Packages that cannot be autolinked yet can be added manually here, for example:
          // packages.add(new MyReactNativePackage());
         packages.add(new DriveQuantPackage());
          return packages;
        }

        @Override
        protected String getJSMainModuleName() {
          return "index";
        }
      };

  @Override
  public ReactNativeHost getReactNativeHost() {
    return mReactNativeHost;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    SoLoader.init(this, /* native exopackage */ false);
    initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
      createNotificationChannel();
      configureDriveKit();
      registerReceiver();
  }


    private void configureDriveKit(){
        DriveKit.INSTANCE.initialize(this);
        DriveKitTripAnalysis.INSTANCE.initialize(createForegroundNotification(), new TripListener() {
            @Override
            public void potentialTripStart(@NonNull StartMode startMode) {

            }

            @Override
                    public void tripStarted(@NotNull StartMode startMode) {
                        Toast.makeText(null, "tripStarted in mode: "+startMode, Toast.LENGTH_LONG).show();
                        //boolean isTripRunning = DriveKitTripAnalysis.INSTANCE.isTripRunning();
                        //makeText(null, "isTripRunning: "+isTripRunning, Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void tripPoint(@NotNull TripPoint tripPoint) {
                    }
                    @Override
                    public void tripSavedForRepost() {
                        Toast.makeText(null, "tripSavedForRepost", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void beaconDetected() {
                    }
                    @Override
                    public void sdkStateChanged(State state) {
                        Toast.makeText(null, "new trip state: "+state, Toast.LENGTH_LONG).show();
                    }
                }
        );


        DriveKit.INSTANCE.setApiKey("NUpEDEzgmqfXu3KRaYvNA9xO"); //getString(R.string.drivekit_api_key)

        initFirstLaunch();
    }

    private  void initFirstLaunch(){
      boolean firstLaunch = DriveKitSharedPreferencesUtils.INSTANCE.getBoolean("dk_demo_firstLaunch", true);
        if (firstLaunch) {
            DriveKitTripAnalysis.INSTANCE.activateAutoStart(true);
            DriveKitTripAnalysis.INSTANCE.setMonitorPotentialTripStart(true);
            DriveKit.INSTANCE.enableLogging("/DriveKit");
            DriveKitTripAnalysis.INSTANCE.setStopTimeOut(5 * 60);
            DriveKitSharedPreferencesUtils.INSTANCE.setBoolean("dk_demo_firstLaunch", false);
        }
    }

    public TripNotification createForegroundNotification(){
        TripNotification tripNotification = new TripNotification(
                "DriveKit SDK",
                "Start a trip with DriveKit SDK",
                R.mipmap.ic_launcher_round);
        return tripNotification;
    }

    private void registerReceiver(){
        TripReceiver receiver = new TripReceiver();
        IntentFilter filter = new IntentFilter("com.drivequant.sdk.TRIP_ANALYSED");
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notif_channel", name, importance);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
  /**
   * Loads Flipper in React Native templates. Call this in the onCreate method with something like
   * initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
   *
   * @param context
   * @param reactInstanceManager
   */
  private static void initializeFlipper(
      Context context, ReactInstanceManager reactInstanceManager) {
    if (BuildConfig.DEBUG) {
      try {
        /*
         We use reflection here to pick up the class that initializes Flipper,
        since Flipper library is not available in release mode
        */
        Class<?> aClass = Class.forName("com.newproject.ReactNativeFlipper");
        aClass
            .getMethod("initializeFlipper", Context.class, ReactInstanceManager.class)
            .invoke(null, context, reactInstanceManager);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }
}

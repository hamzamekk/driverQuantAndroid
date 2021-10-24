package com.newproject;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.drivequant.drivekit.core.DriveKit;
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
      DriveKit.INSTANCE.initialize(this);
      //DriveKit.INSTANCE.setApiKey("NUpEDEzgmqfXu3KRaYvNA9xO");
      DriveKitTripAnalysis.INSTANCE.initialize(createForegroundNotification(), new TripListener() {
          @Override
          public void potentialTripStart(@NonNull StartMode startMode) {

          }

          @Override
          public void tripStarted(@NotNull StartMode startMode) {
          }
          @Override
          public void tripPoint(@NotNull TripPoint tripPoint) {
          }
          @Override
          public void tripSavedForRepost() {
          }
          @Override
          public void beaconDetected() {
          }
          @Override
          public void sdkStateChanged(State state) {
          }
      });
      registerReceiver();
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

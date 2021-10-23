package com.newproject;

import android.content.Context;

import com.drivequant.drivekit.tripanalysis.entity.PostGeneric;
import com.drivequant.drivekit.tripanalysis.entity.PostGenericResponse;
import com.drivequant.drivekit.tripanalysis.receiver.TripAnalysedReceiver;
import com.drivequant.drivekit.tripanalysis.service.recorder.CancelTrip;

import org.jetbrains.annotations.NotNull;

public class TripReceiver extends TripAnalysedReceiver {
    @Override
    public void onTripReceived(
            final Context context,
            final PostGeneric post,
            final PostGenericResponse response) {
    }
    @Override
    public void onTripCancelled(
            @NotNull Context context,
            @NotNull CancelTrip cancelTrip) {
    }
}

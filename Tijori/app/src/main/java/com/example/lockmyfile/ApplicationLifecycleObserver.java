package com.example.lockmyfile;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import androidx.lifecycle.*;
import androidx.annotation.NonNull;

public class ApplicationLifecycleObserver extends Application implements LifecycleObserver{

    public static boolean stillInApp;

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

        //TODO - Need to implement place-down action of Phone
    }

    /**
    // sensor listener
    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // This time step's delta rotation to be multiplied by the current rotation
            // after computing it from the gyro sample data.
            if (timestamp != 0) {
                final float dT = (event.timestamp - timestamp) * NS2S;
                // Axis of the rotation sample, not normalized yet.
                float axisX = event.values[0];
                float axisY = event.values[1];
                float axisZ = event.values[2];

                // Calculate the angular speed of the sample
                float omegaMagnitude = (float) Math.sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);

                // Normalize the rotation vector if it's big enough to get the axis
                if (omegaMagnitude > Math.ulp(1.0)) {
                    axisX /= omegaMagnitude;
                    axisY /= omegaMagnitude;
                    axisZ /= omegaMagnitude;
                }

                // Integrate around this axis with the angular speed by the time step
                // in order to get a delta rotation from this sample over the time step
                // We will convert this axis-angle representation of the delta rotation
                // into a quaternion before turning it into the rotation matrix.
                float thetaOverTwo = omegaMagnitude * dT / 2.0f;
                float sinThetaOverTwo = (float) Math.sin(thetaOverTwo);
                float cosThetaOverTwo = (float) Math.cos(thetaOverTwo);
                deltaRotationVector[0] = sinThetaOverTwo * axisX;
                deltaRotationVector[1] = sinThetaOverTwo * axisY;
                deltaRotationVector[2] = sinThetaOverTwo * axisZ;
                deltaRotationVector[3] = cosThetaOverTwo;

                Log.i("Rotation", String.valueOf(deltaRotationVector[1]));
            }
            timestamp = event.timestamp;
            float[] deltaRotationMatrix = new float[9];
            SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector);

            // User code should concatenate the delta rotation we computed with the current
            // rotation in order to get the updated rotation.
            // rotationCurrent = rotationCurrent * deltaRotationMatrix;

            /*
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accValues = event.values.clone();
            }

            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                geoValues = event.values.clone();
            }

            boolean success = SensorManager.getRotationMatrix(r, i, accValues,
                    geoValues);
            Log.i("Rotation Value", String.valueOf(success));


            if (success) {
                SensorManager.getOrientation(r, v);

                azimuth = (int) (v[0] * (180 / Math.PI));
                pitch = (int) (v[1] * (180 / Math.PI));
                roll = (int) (v[2] * (180 / Math.PI));
                Log.i("Rotation Values", "Azimuth:"+azimuth+"; Pith:"+pitch+"; Roll:"+roll);
            }



/*
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accValues = event.values.clone();
                for(float acc: accValues){
                    //Log.i("Rotation", String.valueOf(acc));
                }

            }

            if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                geoValues = event.values.clone();
                float angle = (float) (geoValues[2]*(180/Math.PI));
                Log.i("Rotation", String.valueOf(angle));
            }

            boolean success = SensorManager.getRotationMatrix(r, i, accValues,
                    geoValues);



            if (success) {
                SensorManager.getOrientation(r, v);

                azimuth = (int)(v[0] * (180 / Math.PI));
                pitch = (int)(v[1] * (180 / Math.PI));
                roll = (int)(v[2] * (180 / Math.PI));

                Log.i("Rotation", String.valueOf(success));
            }





        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
*/



    public void registerLifecycle(Lifecycle lifecycle){
        lifecycle.addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void created() {
        Log.d("SampleLifeCycle", "ON_CREATE");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void started() {
        Log.d("SampleLifeCycle", "ON_START");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void resumed() {
        Log.d("SampleLifeCycle", "ON_RESUME");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void paused() {
        Log.d("SampleLifeCycle", "ON_PAUSE");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stopped() {
        if(stillInApp == false)
            MainActivity.unlocked = false;
        Log.d("SampleLifeCycle", "ON_STOP");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destroyed(){

        Log.d("SampleLifeCycle", "ON_DESTROY");
    }

}

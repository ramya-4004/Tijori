package com.example.lockmyfile.Utilities;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.*;

public class ApplicationLifecycleObserver extends Application implements LifecycleObserver{

    public static boolean stillInApp;

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        stillInApp = false;

        //TODO - Need to implement place-down action of Phone
    }

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
        ApplicationLifecycleObserver.stillInApp = false;
        Log.d("SampleLifeCycle", "ON_STOP");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destroyed(){

        Log.d("SampleLifeCycle", "ON_DESTROY");
    }

}

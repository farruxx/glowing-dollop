package com.farruxx.glowingdollop;

import android.app.Application;
import com.farruxx.glowingdollop.dagger.DaggerServiceComponent;
import com.farruxx.glowingdollop.dagger.ServiceComponent;
import com.farruxx.glowingdollop.dagger.module.AppModule;
import com.farruxx.glowingdollop.dagger.module.ServiceModule;

/**
 * Created by Farruxx on 09.04.2016.
 */
public class App extends Application {
    private ServiceComponent serviceComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        serviceComponent = DaggerServiceComponent.builder()
                .appModule(new AppModule(this))
                .serviceModule(new ServiceModule())
                .build();
    }

    public ServiceComponent getComponent() {
        return serviceComponent;
    }
}

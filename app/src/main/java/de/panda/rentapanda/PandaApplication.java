package de.panda.rentapanda;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.support.annotation.VisibleForTesting;

import javax.inject.Inject;

import de.panda.rentapanda.graph.component.ActivityComponent;
import de.panda.rentapanda.graph.component.DaggerActivityComponent;
import de.panda.rentapanda.graph.module.ApiModule;
import de.panda.rentapanda.graph.module.PresenterModule;
import de.panda.rentapanda.graph.module.ServiceModule;
import de.panda.rentapanda.service.BackgroundService;

/**
 * @author Marcin
 */
public class PandaApplication extends Application implements ComponentCallbacks2 {

    @Inject
    BackgroundService backgroundService;
    private ActivityComponent activityComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        activityComponent = DaggerActivityComponent.builder()
                .apiModule(new ApiModule(this))
                .serviceModule(new ServiceModule())
                .presenterModule(new PresenterModule())
                .build();

        activityComponent.inject(this);
    }

    @Override
    public void onTrimMemory(int level) {
        if (level >= ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN){
            backgroundService.setWasInBackground(true);
        }

        super.onTrimMemory(level);
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    @VisibleForTesting
    public void setActivityComponent(ActivityComponent activityComponent) {
        this.activityComponent = activityComponent;
    }
}

package de.panda.rentapanda;

import android.app.Application;
import android.content.ComponentCallbacks2;

/**
 * @author Marcin
 */
public class PandaApplication extends Application implements ComponentCallbacks2 {

    private boolean wasInBackground = true;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTrimMemory(int level) {
        if (level >= ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN){
            wasInBackground = true;
        }

        super.onTrimMemory(level);
    }

    public boolean wasInBackground() {
        return wasInBackground;
    }

    public void cameFromBackground() {
        wasInBackground = false;
    }
}

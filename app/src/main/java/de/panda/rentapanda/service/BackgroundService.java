package de.panda.rentapanda.service;

/**
 * @author Marcin
 */
public class BackgroundService {
    private boolean wasInBackground = true;

    public boolean wasInBackground () {
        return wasInBackground;
    }

    public void setWasInBackground(boolean wasInBackground) {
        this.wasInBackground = wasInBackground;
    }
}

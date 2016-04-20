package de.panda.rentapanda;

import android.content.Intent;
import android.os.Bundle;

import de.panda.rentapanda.model.ModelJob;
import de.panda.rentapanda.service.BackgroundService;
import de.panda.rentapanda.service.JobService;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Marcin
 */
public class MainPresenter {
    private JobService service;
    private MainActivity activity;
    private Subscription subscription;
    private BackgroundService backgroundService;
    private boolean hasSavedInstance;
    private boolean setParentCalled;

    public MainPresenter(JobService service, BackgroundService backgroundService) {
        this.service = service;
        this.backgroundService = backgroundService;
        setParentCalled = false;
    }

    public void setParent(MainActivity activity, Bundle savedInstanceState) {
        this.activity = activity;
        setParentCalled = true;
        hasSavedInstance = savedInstanceState != null;
    }

    public void onDestroy() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void onStart() {
        boolean wasInBackground = backgroundService.wasInBackground();
        if (setParentCalled || wasInBackground) {
            setParentCalled = false;
            backgroundService.setWasInBackground(false);
            activity.showLoadingView();
            subscription = service.getJobs(hasSavedInstance && !wasInBackground)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(jobs -> activity.setData(jobs),
                            error -> activity.showError(error));
        }
    }

    public void retry() {
        activity.showLoadingView();
        subscription = service.getJobs(false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(jobs -> activity.setData(jobs),
                        error -> activity.showError(error));
    }

    public void onItemClicked(ModelJob job) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_JOB, job);
        activity.startActivity(intent);
    }
}

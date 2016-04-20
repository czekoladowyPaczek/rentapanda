package de.panda.rentapanda;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.panda.rentapanda.service.BackgroundService;
import de.panda.rentapanda.service.JobService;
import de.panda.rentapanda.util.RxSchedulersOverrideRule;
import rx.Observable;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Marcin
 */
public class MainPresenterTest {

    private JobService job;
    private BackgroundService background;
    private MainActivity activity;
    private MainPresenter presenter;

    @Rule
    public RxSchedulersOverrideRule rxSchedulersOverrideRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        job = mock(JobService.class);
        when(job.getJobs(anyBoolean())).thenReturn(Observable.never());

        background = mock(BackgroundService.class);
        activity = mock(MainActivity.class);
        presenter = new MainPresenter(job, background);
    }

    @Test
    public void onStart_shouldCallServiceWhenOnSetParentWasCalled() {
        presenter.setParent(activity, null);

        presenter.onStart();

        verify(job).getJobs(anyBoolean());
    }

    @Test
    public void onStart_shouldCallServiceWhenWasInBackground() {
        when(background.wasInBackground()).thenReturn(true);

        presenter.onStart();

        verify(job).getJobs(anyBoolean());
    }

    @Test
    public void onStart_shouldNotCallServiceWhenSetParentWasNotCalledAndWasNotInBackground() {
        when(background.wasInBackground()).thenReturn(false);

        presenter.onStart();

        verify(job, never()).getJobs(anyBoolean());
    }

    @Test
    public void onStart_shouldRequestCachedWhenWasNotInBackgroundAndHasSavedInstance() {
        when(background.wasInBackground()).thenReturn(false);
        presenter.setParent(activity, new Bundle());

        presenter.onStart();

        verify(job).getJobs(true);
    }

    @Test
    public void onStart_shouldRequestNotCachedWhenWasInBackground() {
        when(background.wasInBackground()).thenReturn(true);

        presenter.onStart();

        verify(job).getJobs(false);
    }

    @Test
    public void onStart_shouldRequestNotCachedWhenHasNoSavedInstance() {
        when(background.wasInBackground()).thenReturn(true);

        presenter.onStart();

        verify(job).getJobs(false);
    }
}
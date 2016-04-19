package de.panda.rentapanda.service;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.panda.rentapanda.api.PandaApi;
import de.panda.rentapanda.database.StorageManager;
import de.panda.rentapanda.model.ModelJob;
import de.panda.rentapanda.model.ModelJobTest;
import rx.Observable;
import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Marcin
 */
public class JobServiceTest {
    private StorageManager storage;
    private PandaApi api;
    private JobService service;

    @Before
    public void setup() {
        storage = mock(StorageManager.class);
        api = mock(PandaApi.class);

        service = new JobService(api, storage);
    }

    @Test
    public void getJobs_shouldGetJobsFromApiAndSaveInDatabaseOnSuccess() throws Exception {
        List<ModelJob> jobs = new ArrayList<>();
        jobs.add(ModelJobTest.getFullModelJob());
        when(api.getJobs()).thenReturn(Observable.just(jobs));

        TestSubscriber<List<ModelJob>> subscriber = new TestSubscriber<>();
        service.getJobs().subscribe(subscriber);

        verify(storage).deleteJobs();
        verify(storage).insertJobs(any(List.class));
        subscriber.assertNoErrors();
        List<ModelJob> actualJobs = subscriber.getOnNextEvents().get(0);
        assertEquals(jobs.size(), actualJobs.size());
    }

    @Test
    public void getJobs_shouldGetJobsFromApiAndDeliverErrorOnError() throws Exception {
        when(api.getJobs()).thenReturn(Observable.error(new Throwable("exception")));

        TestSubscriber<List<ModelJob>> subscriber = new TestSubscriber<>();
        service.getJobs().subscribe(subscriber);

        verify(storage, never()).deleteJobs();
        verify(storage, never()).insertJobs(any(List.class));
        subscriber.assertError(Throwable.class);
    }
}
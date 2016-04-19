package de.panda.rentapanda.service;

import java.util.List;

import de.panda.rentapanda.api.PandaApi;
import de.panda.rentapanda.database.StorageManager;
import de.panda.rentapanda.model.ModelJob;
import rx.Observable;

/**
 * @author Marcin
 */
public class JobService {
    private PandaApi api;
    private StorageManager storageManager;

    public JobService(PandaApi api, StorageManager storage) {
        this.api = api;
        this.storageManager = storage;
    }

    public Observable<List<ModelJob>> getJobs() {
        return api.getJobs()
                .doOnNext(jobs -> {
                    storageManager.deleteJobs();
                    storageManager.insertJobs(jobs);
                });
    }
}

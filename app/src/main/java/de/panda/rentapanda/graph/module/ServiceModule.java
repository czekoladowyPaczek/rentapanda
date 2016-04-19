package de.panda.rentapanda.graph.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.panda.rentapanda.api.PandaApi;
import de.panda.rentapanda.database.StorageManager;
import de.panda.rentapanda.service.JobService;

/**
 * @author Marcin
 */
@Module
public class ServiceModule {
    @Provides
    @Singleton
    JobService provideJobService(PandaApi api, StorageManager storage) {
        return new JobService(api, storage);
    }
}

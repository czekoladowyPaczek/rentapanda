package de.panda.rentapanda.graph.module;

import dagger.Module;
import dagger.Provides;
import de.panda.rentapanda.MainPresenter;
import de.panda.rentapanda.service.BackgroundService;
import de.panda.rentapanda.service.JobService;

/**
 * @author Marcin
 */
@Module
public class PresenterModule {
    @Provides
    MainPresenter provideMainPresenter(JobService service, BackgroundService backgroundService) {
        return new MainPresenter(service, backgroundService);
    }
}

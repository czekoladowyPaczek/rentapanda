package de.panda.rentapanda.graph.component;

import javax.inject.Singleton;

import dagger.Component;
import de.panda.rentapanda.DetailActivity;
import de.panda.rentapanda.MainActivity;
import de.panda.rentapanda.PandaApplication;
import de.panda.rentapanda.graph.module.ApiModule;
import de.panda.rentapanda.graph.module.PresenterModule;
import de.panda.rentapanda.graph.module.ServiceModule;

/**
 * @author Marcin
 */
@Singleton
@Component(modules = {ApiModule.class, ServiceModule.class, PresenterModule.class})
public interface ActivityComponent {
    void inject(MainActivity activity);
    void inject(DetailActivity activity);
    void inject(PandaApplication app);
}

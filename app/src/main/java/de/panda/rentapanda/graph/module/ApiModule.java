package de.panda.rentapanda.graph.module;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.panda.rentapanda.api.PandaApi;
import de.panda.rentapanda.database.StorageManager;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Marcin
 */
@Module
public class ApiModule {
    private Context context;

    public ApiModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    PandaApi providePandaApi() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PandaApi.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(PandaApi.class);
    }

    @Provides
    StorageManager provideStorageManager() {
        return new StorageManager(context);
    }
}

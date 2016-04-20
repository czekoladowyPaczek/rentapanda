package de.panda.rentapanda.api;

import java.util.List;

import de.panda.rentapanda.model.ModelJob;
import retrofit2.http.GET;
import rx.Observable;

/**
 * @author Marcin
 */
public interface PandaApi {
    String ENDPOINT = "http://private-anon-3d8482944-rentapanda.apiary-mock.com/";

    @GET ("/jobs")
    Observable<List<ModelJob>> getJobs();
}

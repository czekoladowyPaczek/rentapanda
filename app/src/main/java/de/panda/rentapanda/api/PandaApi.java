package de.panda.rentapanda.api;

import java.util.List;

import de.panda.rentapanda.model.ModelJob;
import retrofit2.http.GET;
import rx.Observable;

/**
 * @author Marcin
 */
public interface PandaApi {
    String ENDPOINT = "http://rentapanda.apiblueprint.org";

    @GET ("/jobs")
    Observable<List<ModelJob>> getJobs();
}

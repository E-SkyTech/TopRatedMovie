package esky.tech.topratedmovies.Remote;

import esky.tech.topratedmovies.Model.RespuestaPeliculas;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("movie/top_rated")
    Call<RespuestaPeliculas> getTopRatedMovies(@Query("api_key") String apiKey);
}

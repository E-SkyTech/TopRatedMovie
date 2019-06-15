package esky.tech.topratedmovies.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RespuestaPeliculas {

    @SerializedName("results")
    private List<Peliculas> results;

    public List<Peliculas> getResults() {
        return results;
    }

}

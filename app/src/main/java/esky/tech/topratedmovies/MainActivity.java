package esky.tech.topratedmovies;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import esky.tech.topratedmovies.Adapter.CustomViewPager;
import esky.tech.topratedmovies.Common.Common;
import esky.tech.topratedmovies.Model.Peliculas;
import esky.tech.topratedmovies.Model.RespuestaPeliculas;
import esky.tech.topratedmovies.Remote.APIService;
import esky.tech.topratedmovies.Remote.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private MultiSnapRecyclerView recyclerView;
    private CustomViewPager adapter;
    private List<Peliculas> listaPeliculas;
    private SwipeRefreshLayout swipeRefreshLayout;
    //public static final String LOG_TAG_ = CustomViewPager.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*-- VERIFICANDO CONEXIÓN A INTERNET ANTES DE INICIAR VISTA --*/

        if (Common.conexionAInternetActiva(getBaseContext())) {
            cargarTopRated();
        } else{
            Toast.makeText(getBaseContext(), "Por favor revisa tu conexión a internet", Toast.LENGTH_SHORT).show();
            return;
        }
        /*-- FIN VERIFICANDO CONEXIÓN A INTERNET --*/

        /*-- ACTUALIZAR VISTA DE USUARIO Y LISTA DE PELÍCULAS --*/

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.actividad_principal);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Common.conexionAInternetActiva(getBaseContext())) {
                    //iniciarVistas();
                    cargarTopRated();
                    Toast.makeText(MainActivity.this, "Se actualizó la página", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getBaseContext(), "Por favor revisa tu conexión a internet", Toast.LENGTH_SHORT).show();
                    if (swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    return;
                }
            }
        });

        /* FIN DE ACCIÓN ACTUALIZAR */
    }

    /*-- SOLICITAR PELÍCULAS TOP RATED --*/

    private void cargarTopRated() {
        try{
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(getApplicationContext(), "Es necesario tener acceso con API Key",Toast.LENGTH_SHORT).show();
                return;
            }

            RetrofitClient retrofitClient = new RetrofitClient();
            APIService apiService =
                    RetrofitClient.getClient().create(APIService.class);
            Call<RespuestaPeliculas> call = apiService.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<RespuestaPeliculas>() {
                @Override
                public void onResponse(Call<RespuestaPeliculas> call, Response<RespuestaPeliculas> response) {
                    listaPeliculas = response.body().getResults();
                    if (response.isSuccessful()){
                        if (response.body() != null){
                            adapter = new CustomViewPager(getApplicationContext(), listaPeliculas);
                            recyclerView = (MultiSnapRecyclerView)findViewById(R.id.recycler_view);
                            LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(adapter);
                            recyclerView.smoothScrollToPosition(0);
                            if (swipeRefreshLayout.isRefreshing()){
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespuestaPeliculas> call, Throwable t) {
                    Log.d("Error",t.getMessage());
                    Toast.makeText(MainActivity.this, "Error buscando información",Toast.LENGTH_SHORT).show();

                }
            });
        } catch (Exception e){
            Log.d("Error",e.getMessage());
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    /*-- FIN SOLICITUD PELÍCULAS TOP RATED --*/

}

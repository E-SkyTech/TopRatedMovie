package esky.tech.topratedmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActividadDetalles extends AppCompatActivity {

    @BindView(R.id.titulo)
    TextView nombrePelicula;
    @BindView(R.id.tramasinopsis)
    TextView tramaSinopsis;
    @BindView(R.id.calificacionUsuario)
    TextView calificacionUsuario;
    @BindView(R.id.fechaLanzamiento)
    TextView fechaLanzamiento;
    @BindView(R.id.thumbnail)
    ImageView thumbnail;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.actividad_principal)
    CoordinatorLayout actividadPrincipal;
    @BindView(R.id.btn_Volver)
    FloatingActionButton btnVolver;
    @BindView(R.id.layout_rating)
    LinearLayout layoutRating;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_detalle);

        ButterKnife.bind(this);

        Intent intentInicioActividad = getIntent();
        if (intentInicioActividad.hasExtra("original_title")) {

            String thumbnail_header = getIntent().getExtras().getString("poster_path");
            String movieName = getIntent().getExtras().getString("original_title");
            String synopsis = getIntent().getExtras().getString("overview");
            String rating = getIntent().getExtras().getString("vote_average");
            String dateOfRelease = getIntent().getExtras().getString("release_date");

            Glide.with(this)
                    .load(thumbnail_header)
                    .apply(new RequestOptions().placeholder(R.drawable.load).error(R.drawable.error))
                    .into(thumbnail);

            nombrePelicula.setText(movieName);
            tramaSinopsis.setText(synopsis);
            calificacionUsuario.setText(rating);
            fechaLanzamiento.setText(dateOfRelease);
        } else {
            Toast.makeText(this, "Sin información", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_Volver)
    public void onBackPressed() {
        super.onBackPressed();
    }
}

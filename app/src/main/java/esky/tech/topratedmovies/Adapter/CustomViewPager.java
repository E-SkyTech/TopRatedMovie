package esky.tech.topratedmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import esky.tech.topratedmovies.ActividadDetalles;
import esky.tech.topratedmovies.Model.Peliculas;
import esky.tech.topratedmovies.R;

public class CustomViewPager extends RecyclerView.Adapter<CustomViewPager.MyViewHolder> {

    @BindView(R.id.thumbnail)
    ImageView thumbnail;
    @BindView(R.id.titulo)
    TextView titulo;
    @BindView(R.id.calificacionUsuario)
    TextView calificacionUsuario;
    @BindView(R.id.card_view)
    CardView cardView;
    private Context mContext;
    private List<Peliculas> listaPeliculas;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titulo, calificacionUsuario;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            titulo = (TextView) view.findViewById(R.id.titulo);
            calificacionUsuario = (TextView) view.findViewById(R.id.calificacionUsuario);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Peliculas clickedDataItem = listaPeliculas.get(pos);
                        Intent intent = new Intent(mContext, ActividadDetalles.class);
                        intent.putExtra("original_title", listaPeliculas.get(pos).getOriginalTitle());
                        intent.putExtra("poster_path", listaPeliculas.get(pos).getPosterPath());
                        intent.putExtra("overview", listaPeliculas.get(pos).getOverview());
                        intent.putExtra("vote_average", Double.toString(listaPeliculas.get(pos).getVoteAverage()));
                        intent.putExtra("release_date", listaPeliculas.get(pos).getReleaseDate());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    public CustomViewPager(Context mContext, List<Peliculas> listaPeliculas) {
        this.mContext = mContext;
        this.listaPeliculas = listaPeliculas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cuadro_pelicula, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, int i) {
        viewHolder.titulo.setText(listaPeliculas.get(i).getOriginalTitle());
        String vote = Double.toString(listaPeliculas.get(i).getVoteAverage());
        viewHolder.calificacionUsuario.setText(vote);

        Glide.with(mContext)
                .load(listaPeliculas.get(i).getPosterPath())
                .apply(new RequestOptions().placeholder(R.drawable.load).error(R.drawable.error))
                .into(viewHolder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return listaPeliculas.size();
    }
}

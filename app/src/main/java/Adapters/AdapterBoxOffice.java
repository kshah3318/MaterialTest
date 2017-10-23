package Adapters;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.karan.materialtest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Models.Movie;
import network.VolleySingleton;

/**
 * Created by Admin on 10/19/2017.
 */

public class AdapterBoxOffice extends RecyclerView.Adapter<AdapterBoxOffice.ViewHolderBoxOffice> {
    private LayoutInflater layoutInflater;
    private ArrayList<Movie> movieArrayList=new ArrayList<>();
    private VolleySingleton mvVolleySingleton;
    private ImageLoader miImageLoader;
    Context context;
    public AdapterBoxOffice(Context context)
    {
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        mvVolleySingleton=VolleySingleton.getInstance();
        miImageLoader=mvVolleySingleton.getImageLoader();
    }
    public void setMovieList(ArrayList<Movie> movieArrayList)
    {
        this.movieArrayList=movieArrayList;
        notifyItemRangeChanged(0,movieArrayList.size());
    }
    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.custom_movie_box_office,parent,false);
        ViewHolderBoxOffice mbViewHolderBoxOffice=new ViewHolderBoxOffice(view);
        return mbViewHolderBoxOffice;
    }

    @Override
    public void onBindViewHolder(final ViewHolderBoxOffice holder, int position) {
        Movie currentMovie=movieArrayList.get(position);
        holder.movie_name.setText(currentMovie.getMovie_title());
        holder.movie_synopsis.setText(currentMovie.getSyopsis());
        holder.rate_movie.setRating(currentMovie.getRating());
        holder.movie_release.setText(currentMovie.getReleaseDateTheartre().toString());
        if(currentMovie.getPoster_path()!=null)
        {
            /*miImageLoader.get(currentMovie.getPoster_path(), new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.movie_pic.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });*/
            Picasso.with(context)
                    .load(currentMovie.getPoster_path())
                    .into(holder.movie_pic);

        }
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    static class ViewHolderBoxOffice extends RecyclerView.ViewHolder
    {
        public ImageView movie_pic;
        public TextView movie_name;
        public TextView movie_synopsis;
        public RatingBar rate_movie;
        public TextView movie_release;
        public ViewHolderBoxOffice(View itemView) {
            super(itemView);
            movie_pic=(ImageView)itemView.findViewById(R.id.movie_photo);
            movie_name=(TextView)itemView.findViewById(R.id.movie_name);
            movie_synopsis=(TextView)itemView.findViewById(R.id.sypnosis);
            rate_movie=(RatingBar)itemView.findViewById(R.id.movie_rate_bar);
            movie_release=(TextView)itemView.findViewById(R.id.movie_date);
        }
    }
}

package fragments;


import android.app.VoiceInteractor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.karan.materialtest.Activity.MyApplication;
import com.karan.materialtest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Models.Movie;
import extras.Keys;
import network.VolleySingleton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_BoxOffice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_BoxOffice extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingleton mVolleySingleton;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;
    public static final String box_office_url="https://api.themoviedb.org/3/movie/now_playing?";
    public static  String url="https://api.themoviedb.org/3/movie/now_playing?api_key=47ec37919c0cd2b9fa96494103a3b838&language=en-US&page=";
    private ArrayList<Movie> movies=new ArrayList<>();
    private DateFormat mdDateFormat=new SimpleDateFormat("yyyy-MM-dd");
    public Fragment_BoxOffice() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_BoxOffice.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_BoxOffice newInstance(String param1, String param2) {
        Fragment_BoxOffice fragment = new Fragment_BoxOffice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static  String getRequestUrl(int limit)
    {
        return url+limit;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mVolleySingleton=VolleySingleton.getInstance();
        mRequestQueue=mVolleySingleton.getRequestQueue();
        SendJsonRequest();
    }
    private void SendJsonRequest()
    {
        JsonObjectRequest mrJsonObjectRequest=new JsonObjectRequest(Request.Method.GET,getRequestUrl(10),new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               parseJsonResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        mRequestQueue.add(mrJsonObjectRequest);
    }
    private void parseJsonResponse(JSONObject response)
    {
        if(response==null || response.length()==0)
        {
            return;
        }
        try {
            StringBuilder msStringBuilder=new StringBuilder();
            JSONArray arrayMovies=response.getJSONArray(Keys.EndPointBoxOffice.Movie_KEY);
            for(int i=0;i<arrayMovies.length();i++)
            {
                JSONObject currentMovie=arrayMovies.getJSONObject(i);
                String movie_id=currentMovie.getString(Keys.EndPointBoxOffice.Movie_ID);
                String movie_name=currentMovie.getString(Keys.EndPointBoxOffice.Movie_TITLE);
                String movie_synopsis=currentMovie.getString(Keys.EndPointBoxOffice.Movie_SYNOPSIS);
                String movie_release_date=currentMovie.getString(Keys.EndPointBoxOffice.Movie_RELEASE_DATE);
                String movie_rating=currentMovie.getString(Keys.EndPointBoxOffice.Movie_RATING);
                String movie_poster=currentMovie.getString(Keys.EndPointBoxOffice.Movie_POSTER_PATH);
                Movie movie=new Movie();
                movie.setMovie_id(movie_id);
                movie.setMovie_title(movie_name);
                Date date=mdDateFormat.parse(movie_release_date);
                movie.setReleaseDateTheartre(date);
                movie.setSyopsis(movie_synopsis);
                movie.setRating(Double.valueOf(movie_rating));
                movie.setPoster_path(movie_poster);
                msStringBuilder.append(movie.getMovie_id()+"----------"+movie.getPoster_path()+"\n");

                movies.add(movie);
            }
            Toast.makeText(getActivity(),msStringBuilder.toString(),Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__box_office, container, false);
    }

}

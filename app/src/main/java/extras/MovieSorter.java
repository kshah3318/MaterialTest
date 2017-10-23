package extras;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Models.Movie;

/**
 * Created by Admin on 10/23/2017.
 */

public class MovieSorter {
    public void sortMoviesByName(ArrayList<Movie> movies)
    {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return o1.getMovie_title().compareTo(o2.getMovie_title());
            }
        });
    }
    public void sortMoviesByDate(ArrayList<Movie> movies)
    {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return o1.getReleaseDateTheartre().compareTo(o2.getReleaseDateTheartre());
            }
        });
    }
    public void sortMoviesByRate(ArrayList<Movie> movies)
    {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                float rating1=o1.getRating();
                float rating2=o2.getRating();
                if(rating1<rating2)
                {
                    return -1;
                }
                else if(rating1>rating2)
                {
                    return 1;
                }
                else
                {
                    return 0;
                }

            }
        });
    }
}

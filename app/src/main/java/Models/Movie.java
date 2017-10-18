package Models;

import java.util.Date;

/**
 * Created by Admin on 10/18/2017.
 */

public class Movie {
    private String movie_id;

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public Date getReleaseDateTheartre() {
        return releaseDateTheartre;
    }

    public void setReleaseDateTheartre(Date releaseDateTheartre) {
        this.releaseDateTheartre = releaseDateTheartre;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getSyopsis() {
        return syopsis;
    }

    public void setSyopsis(String syopsis) {
        this.syopsis = syopsis;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    private String movie_title;
    private Date releaseDateTheartre;
    private int vote_count;
    private String syopsis;
    private double rating;
    private String poster_path;
}

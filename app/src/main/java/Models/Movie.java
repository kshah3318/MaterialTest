package Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Admin on 10/18/2017.
 */

public class Movie implements Parcelable{
    private String movie_id;

    public Movie(Parcel in) {
        movie_id = in.readString();
        movie_title = in.readString();
        releaseDateTheartre=new Date(in.readLong());
        vote_count = in.readInt();
        syopsis = in.readString();
        rating = in.readFloat();
        poster_path = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie() {
        
    }

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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
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
    private float rating;
    private String poster_path;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(movie_id);
        dest.writeString(movie_title);
        dest.writeLong(releaseDateTheartre.getTime());
        dest.writeInt(vote_count);
        dest.writeString(syopsis);
        dest.writeFloat(rating);
        dest.writeString(poster_path);
    }

}

package extras;

/**
 * Created by Admin on 10/23/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import Models.Movie;
/**
 * Created by Admin on 10/23/2017.
 */
public class MovieDatabase
{
    private MoviesHelper moviesHelper;
    private SQLiteDatabase mSqLiteDatabase;
    public MovieDatabase(Context context)
    {
        moviesHelper=new MoviesHelper(context);
        mSqLiteDatabase=moviesHelper.getWritableDatabase();
    }
    public void insertMoviesBoxOffice(ArrayList<Movie> listMovie,boolean clearPrevious)
    {
        if(clearPrevious)
        {
            deleteAll();
        }
        String sql=" INSERT INTO "+MoviesHelper.Table_box_office + " VALUES (?,?,?,?,?,?);";
        SQLiteStatement statement=mSqLiteDatabase.compileStatement(sql);
        mSqLiteDatabase.beginTransaction();
        //Continue from here tommorrow
    }

    private void deleteAll() {
    }

    public class MoviesHelper extends SQLiteOpenHelper
    {
        private Context mcContext;
        private static final String db_name = "movie_db";
        private static final int db_version = 1;
        public static final String Table_box_office = "movies_box_office";
        public static final String MOVIE_ID = "_id";
        public static final String MOVIE_NAME = "_name";
        public static final String MOVIE_DATE = "_release_date";
        public static final String MOVIE_VOTES = "_rating";
        public static final String MOVIE_SYSNOPSIS = "_synopsis";
        public static final String MOVIE_POSTER = "_poster_path";
        private static final String CREATE_MOVIE_BOX_OFFICE = " CREATE TABLE " + Table_box_office + " ( " +
                MOVIE_ID + " INTEGER PRIMARY KEY ," +
                MOVIE_NAME + " TEXT," +
                MOVIE_DATE + "INTEGER," +
                MOVIE_VOTES + "FLOAT," +
                MOVIE_SYSNOPSIS + "TEXT," +
                MOVIE_POSTER + "TEXT," +
                ");";

        public MoviesHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        public  MoviesHelper(Context context)
        {
            super(context,db_name,null,db_version);
            mcContext=context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}

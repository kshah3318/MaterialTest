package fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.karan.materialtest.Activity.SubActivity;
import com.karan.materialtest.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.CustomAdapter;
import Models.DataStore;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNavigationDrawer extends Fragment implements CustomAdapter.ClickListener {

    public static final String PREF_FILE_NAME = "test_pref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private boolean mUserLearned;
    private boolean FromSavedInstanceState;
    private View ContainerView;
    private RecyclerView mRecyclerView;
    private CustomAdapter mCustomAdapter;
    public static DataStore current;
    public static  List<DataStore> mdDataStores;
    public FragmentNavigationDrawer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearned = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            FromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_fragment_navigation_drawer, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.drawer_list);
        mCustomAdapter=new CustomAdapter(getActivity(),getData());
        mCustomAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mCustomAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return mView;
    }

    public static List<DataStore> getData() {
        mdDataStores=new ArrayList<DataStore>();
        int resource_id[]={R.drawable.ic_home,R.drawable.ic_photo,R.drawable.ic_movies,R.drawable.ic_notification};
        String titles[]={"Home","Photos","Movies","Notification"};
        for(int i=0;i<resource_id.length;i++)
        {
            current=new DataStore();
            current.icon_id=resource_id[i];
            current.title=titles[i];
            mdDataStores.add(current);
        }
        return  mdDataStores;
    }

    public void setUp(int fragment_id, DrawerLayout drawerLayout, final Toolbar toolbar) {
        ContainerView = getActivity().findViewById(fragment_id);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
                if (!mUserLearned) {
                    mUserLearned = true;
                    saveToPreference(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearned + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
               // Log.d("karan", "offset" + slideOffset);
                //If you want to lighten the color of toolbar when navigation bar is opened at that time the below given code is used
                if (slideOffset < 0.6)
                    toolbar.setAlpha(1 - slideOffset);
            }
        };
        if (!mUserLearned && !FromSavedInstanceState) {
            mDrawerLayout.openDrawer(ContainerView);
        }
        /*mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        */

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


    }

    public static void saveToPreference(Context cxt, String preferencesName, String preferencesValue) {
        SharedPreferences sharedPreferences = cxt.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferencesName, preferencesValue);
        editor.apply();

    }

    public static String readFromPreferences(Context cxt, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = cxt.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

    @Override
    public void itemClicked(View v, int pos) {
        startActivity(new Intent(getActivity(),SubActivity.class));
    }
}

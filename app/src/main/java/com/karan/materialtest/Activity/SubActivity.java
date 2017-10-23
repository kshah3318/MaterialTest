package com.karan.materialtest.Activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.karan.materialtest.R;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import extras.SortListener;
import fragments.Fragment_BoxOffice;
import fragments.Fragment_Search;
import fragments.Fragment_Upcoming;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;
import me.tatarka.support.os.PersistableBundle;
import services.MyServices;

public class SubActivity extends ActionBarActivity implements MaterialTabListener,View.OnClickListener {


    private static final int JOB_ID = 100;
    private MaterialTabHost materialTabHost;
    private Toolbar toolbar;
    private ViewPager mViewPager;
    public static final int MOVIES_SEARCH_RESULT = 0;
    public static final int MOVIES_HITS = 1;
    public static final int MOVIES_UPCOMING = 2;
    public static final String TAG_SORT_DATE = "sortDate";
    public static final String TAG_SORT_NAME = "sortName";
    public static final String TAG_SORT_RATE = "sortRatings";
    private JobScheduler mJobScheduler;
    ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        mJobScheduler=JobScheduler.getInstance(this);
        ConstructJob();
        Intent io = getIntent();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        materialTabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        mViewPager = (ViewPager) findViewById(R.id.SubPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int pos) {
                materialTabHost.setSelectedNavigationItem(pos);
                //mViewPager.setOffscreenPageLimit(0);
            }
        });
        for (int i = 0; i < adapter.getCount(); i++) {
            materialTabHost.addTab(
                    materialTabHost.newTab()
                            //.setText(adapter.getPageTitle(i))
                            .setTabListener(this)
                            .setIcon(adapter.getIcon(i))
            );
        }
        //Android code to add floating Action Button with its sub Action Button
        setUpFloatingButton();
    }
    private void ConstructJob()
    {
        JobInfo.Builder builder=new JobInfo.Builder(JOB_ID,new ComponentName(this, MyServices.class));
        builder.setPeriodic(2000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .build();
            mJobScheduler.schedule(builder.build());
    }

    public void setUpFloatingButton() {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.ic_add);
        com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton mFloatingActionButton = new com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.Builder(this)
                .setContentView(imageView)
                .build();
            ImageView iconSortName=new ImageView(this);
            iconSortName.setImageResource(R.drawable.ic_movie_name);
            ImageView iconSortDate=new ImageView(this);
            iconSortDate.setImageResource(R.drawable.ic_rating);
            ImageView iconSortRate=new ImageView(this);
            iconSortRate.setImageResource(R.drawable.ic_date_filter);
        SubActionButton.Builder itemBuilder=new SubActionButton.Builder(this);
        SubActionButton buttonSortByName=itemBuilder.setContentView(iconSortName).build();
        SubActionButton buttonSortByDate=itemBuilder.setContentView(iconSortDate).build();
        SubActionButton buttonSortByRate=itemBuilder.setContentView(iconSortRate).build();
        buttonSortByName.setOnClickListener(this);
        buttonSortByRate.setOnClickListener(this);
        buttonSortByDate.setOnClickListener(this);
        buttonSortByName.setTag(TAG_SORT_NAME);
        buttonSortByRate.setTag(TAG_SORT_RATE);
        buttonSortByDate.setTag(TAG_SORT_DATE);

        FloatingActionMenu actionMenu=new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonSortByName)
                .addSubActionView(buttonSortByDate)
                .addSubActionView(buttonSortByRate)
                .attachTo(mFloatingActionButton)
                .build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu_sub, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    private void search(SearchView searchView) {


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment= (Fragment) adapter.instantiateItem(mViewPager,mViewPager.getCurrentItem());
        if(v.getTag().equals(TAG_SORT_NAME)) {
               //Toast.makeText(this,"Name Clicked",Toast.LENGTH_LONG).show();
               if(fragment instanceof SortListener)
               {
                   ((SortListener) fragment).onSortByName();
               }
            if(v.getTag().equals(TAG_SORT_RATE))
            {
                //  //Toast.makeText(this,"Rate Clicked",Toast.LENGTH_LONG).show();
                ((SortListener) fragment).onSortByRating();

            }
            if(v.getTag().equals(TAG_SORT_DATE))
            {
                ((SortListener) fragment).onSortByDate();

            }
           }

    }


    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        //Code to add icons on the tabs of tabhost
        int icons[] = {R.drawable.ic_home, R.drawable.ic_movies, R.drawable.ic_notification};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            //return MyFragment.getInstance(position);
            Fragment mfragment = null;
            switch (position) {
                case MOVIES_SEARCH_RESULT:
                    mfragment = Fragment_BoxOffice.newInstance("", "");
                    break;

                case MOVIES_HITS:
                    mfragment = Fragment_Search.newInstance("", "");
                    break;

                case MOVIES_UPCOMING:
                    mfragment = Fragment_Upcoming.newInstance("", "");
                    break;
            }
            return mfragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int pos) {
            return getResources().getStringArray(R.array.tabs)[pos];
        }

        private Drawable getIcon(int pos) {
            return getResources().getDrawable(icons[pos]);
        }
    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }


    public static class MyFragment extends Fragment {
        private TextView mTextView;

        public static MainActivity.MyFragment getInstance(int pos) {
            MainActivity.MyFragment myFragment = new MainActivity.MyFragment();
            Bundle args = new Bundle();
            args.putInt("positions", pos);
            myFragment.setArguments(args);
            return myFragment;

        }

        @Override
        public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
            View layout = layoutInflater.inflate(R.layout.fragment_my, container, false);
            mTextView = (TextView) layout.findViewById(R.id.position);
            Bundle bundle = getArguments();
            if (bundle != null) {
                mTextView.setText("The page currently selected is->" + bundle.getInt("positions"));
            }
            //Android code to setup floating action button
            return layout;
        }

    }
}

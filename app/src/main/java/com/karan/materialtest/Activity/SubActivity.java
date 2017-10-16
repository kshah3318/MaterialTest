package com.karan.materialtest.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karan.materialtest.R;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class SubActivity extends ActionBarActivity implements MaterialTabListener {


    private MaterialTabHost materialTabHost;
    private Toolbar toolbar;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Intent io = getIntent();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        materialTabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        mViewPager = (ViewPager) findViewById(R.id.SubPager);
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int pos)
            {
                materialTabHost.setSelectedNavigationItem(pos);
            }
        });
        for(int i=0;i<adapter.getCount();i++)
        {
            materialTabHost.addTab(
                    materialTabHost.newTab()
                    //.setText(adapter.getPageTitle(i))
                    .setTabListener(this)
                    .setIcon(adapter.getIcon(i))
            );
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        //Code to add icons on the tabs of tabhost
        int icons[]={R.drawable.ic_home,R.drawable.ic_movies,R.drawable.ic_notification};
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MyFragment.getInstance(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int pos)
        {
            return getResources().getStringArray(R.array.tabs)[pos];
        }
        private Drawable getIcon(int pos)
        {
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


    public static class MyFragment extends Fragment
    {
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
            return layout;
        }
    }
}

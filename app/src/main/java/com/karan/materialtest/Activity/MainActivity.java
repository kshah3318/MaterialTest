package com.karan.materialtest.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karan.materialtest.R;

import org.w3c.dom.Text;

import network.VolleySingleton;
import tabs.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager mViewPager;
    private SlidingTabLayout msSlidingTabLayout;
    private FragmentNavigationDrawer fragmentNavigationDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //by implementing activity main appbar  drawer will be open under the toolbar
        //setContentView(R.layout.activity_main_appbar);
        //By implementing activity main xml file toolbar will be hidden
        //under the drawer
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        //Code related to make slidingTabLayout,Pager and Fragment
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        msSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        msSlidingTabLayout.setCustomTabView(R.layout.custom_tabs,R.id.tabText);
        msSlidingTabLayout.setDistributeEvenly(true);
        msSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.ColorWhite);
            }
        });
        msSlidingTabLayout.setViewPager(mViewPager);

        fragmentNavigationDrawer = (FragmentNavigationDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
        fragmentNavigationDrawer.setUp(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.main_drawer), toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this, "You just hit" + item.getTitle(), Toast.LENGTH_LONG).show();
        }
        if(id==R.id.tabswithlibrary)
        {
            Intent io = new Intent(this, SubActivity.class);
            startActivity(io);

        }
        if (id == R.id.navigation) {
            Intent io = new Intent(this, SubActivity.class);
            startActivity(io);

        }
        return super.onOptionsItemSelected(item);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        String tabs[];
        int icons[] = {R.drawable.ic_home, R.drawable.ic_movies, R.drawable.ic_photo};
        String tabText[] = getResources().getStringArray(R.array.tabs);

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            MyFragment myFragment = MyFragment.getInstance(position);
            return myFragment;
        }

        //Code to add the icons in the sliding TabLayout by converting into screen with the help of SpannableString
        //and the ImageSpan
        @Override
        public CharSequence getPageTitle(int position) {
            Drawable drawable=getResources().getDrawable(icons[position]);
            drawable.setBounds(0,0,100,100);
            ImageSpan mImageSpan=new ImageSpan(drawable);
            SpannableString msSpannableString=new SpannableString(" ");
            msSpannableString.setSpan(mImageSpan,0,msSpannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return msSpannableString;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public static class MyFragment extends Fragment {
        private TextView mTextView;

        public static MyFragment getInstance(int pos) {
            MyFragment myFragment = new MyFragment();
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
            RequestQueue mrRequestQueue= VolleySingleton.getInstance().getRequestQueue();
            StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://php.net/",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            //mTextView.setText("Response is: "+ response.toString());
                            Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mTextView.setText("That didn't work!");
                }
            });
            mrRequestQueue.add(stringRequest);
            return layout;
        }
    }
}

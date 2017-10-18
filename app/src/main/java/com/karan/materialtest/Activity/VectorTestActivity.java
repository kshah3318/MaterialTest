package com.karan.materialtest.Activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.karan.materialtest.R;
import com.telly.mrvector.MrVector;

import extras.Util;

public class VectorTestActivity extends AppCompatActivity {
    Toolbar mToolbar;
    ImageView imageView;
    Drawable mDrawable=null;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_test);
        mToolbar=(Toolbar)findViewById(R.id.app_bar);
        imageView=(ImageView)findViewById(R.id.vectorImageClock);
        mDrawable= MrVector.inflate(getResources(),R.drawable.vector_clock);
        if(Util.isLollipopOrGreater())
        {
            mDrawable=MrVector.inflate(getResources(),R.drawable.animator_vector_clock);
        }
        else
        {
            mDrawable=MrVector.inflate(getResources(),R.drawable.vector_clock);
        }
        if(Util.isJellyBeanOrGreater())
        {
            imageView.setBackground(mDrawable);
        }
        else
        {
            imageView.setBackgroundDrawable(mDrawable);
        }
        if(mDrawable instanceof Animatable)
        {
            ((Animatable)mDrawable).start();
        }
    }
}

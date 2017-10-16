package network;
import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.karan.materialtest.Activity.MyApplication;

/**
 * Created by Admin on 10/16/2017.
 */

public class VolleySingleton {
    private static VolleySingleton mvVolleySingleton=null;
    private RequestQueue mrRequestQueue;
    private ImageLoader mImageLoader;

    private VolleySingleton()
    {
        mrRequestQueue= Volley.newRequestQueue(MyApplication.getAppContext());
        //code to store images in cache with the help of ImageLoader
        mImageLoader=new ImageLoader(mrRequestQueue, new ImageLoader.ImageCache() {
            private LruCache<String,Bitmap> cache=new LruCache<>((int)Runtime.getRuntime().maxMemory()/1024/8);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url,bitmap);
            }
        });
    }
    public static VolleySingleton getInstance()
    {
        if(mvVolleySingleton==null)
        {
            mvVolleySingleton=new VolleySingleton();
        }
        return  mvVolleySingleton;
    }
    public  RequestQueue getRequestQueue()
    {
        return mrRequestQueue;
    }
    public ImageLoader getImageLoader()
    {
        return mImageLoader;
    }
}

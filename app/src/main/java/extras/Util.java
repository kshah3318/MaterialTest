package extras;

import android.os.Build;

/**
 * Created by Admin on 10/17/2017.
 */

public class Util {
    public static boolean isLollipopOrGreater()
    {
        return Build.VERSION.SDK_INT >= 21 ? true : false;
    }
    public static boolean isJellyBeanOrGreater()
    {
        return Build.VERSION.SDK_INT >= 16 ? true : false;
    }
}

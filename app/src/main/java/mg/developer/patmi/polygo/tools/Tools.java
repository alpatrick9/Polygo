package mg.developer.patmi.polygo.tools;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import mg.developer.patmi.polygo.R;

/**
 * Created by patmi on 10/02/2017.
 */
public class Tools {

    public static Integer getWidth(Context context, Integer nbCol) {

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = ((Activity) context).getWindowManager();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        //int hauteur = metrics.heightPixels;
        int width = metrics.widthPixels;
        return (int) Math.ceil(width / (float) nbCol) - 5;
    }

    public static void replaceFragment(Context context, Fragment fragment) {
        if (fragment != null) {
            RelativeLayout maLayout = (RelativeLayout) ((Activity) context).findViewById(R.id.contenaire);
            maLayout.removeAllViews();
            FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.contenaire, fragment).commit();
        }
    }
}

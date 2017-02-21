package mg.developer.patmi.polygo.tools;

import android.content.Context;

/**
 * Created by developer on 2/21/17.
 */

public class PathManager {

    public static String getRootPath(Context context) {
        String rootPath = context.getFilesDir().getPath(); // /data/data/com.package.nom
        return rootPath.substring(0, rootPath.lastIndexOf("/")) + "/Polygo/";
    }
}

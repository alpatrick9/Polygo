package mg.developer.patmi.polygo.tools.bean_manager;

import android.content.Context;

import mg.developer.patmi.polygo.models.bean.Default;
import mg.developer.patmi.polygo.tools.json_manager.DefaultDataJsonManager;

/**
 * Created by developer on 2/21/17.
 */

public class DefaultDataManager {

    public static Default getDefaultData(Context context) {
        return DefaultDataJsonManager.jsonReader(context);
    }

    public static void setDefaultData(Context context, Default data) {
        DefaultDataJsonManager.jsonWriter(context,data);
    }

    public static boolean isDefaultSet(Context context) {
        Default data = DefaultDataManager.getDefaultData(context);
        return
                data.getV0Depart() != null &&
                data.getxStation() != null &&
                data.getyStation() != null;
    }
}

package mg.developer.patmi.polygo.tools.bean_manager;

import mg.developer.patmi.polygo.models.bean.Default;

/**
 * Created by developer on 2/21/17.
 */

public class DefaultDataManager {

    private static Default data = new Default();

    public static Default getDefaultData() {
        // à recuperer d'un fichier json
        return data;
    }

    public static void setDefaultData(Default data) {
        // modifier le fichier json
        DefaultDataManager.data = data;
    }

    public static boolean isDefaultSet() {
        Default data = DefaultDataManager.getDefaultData();
        return
                data.getV0Depart() != null &&
                data.getxStation() != null &&
                data.getyStation() != null;
    }
}

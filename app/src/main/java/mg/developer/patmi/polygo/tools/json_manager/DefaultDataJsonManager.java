package mg.developer.patmi.polygo.tools.json_manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import mg.developer.patmi.polygo.models.bean.Default;
import mg.developer.patmi.polygo.tools.PathManager;

/**
 * Created by developer on 2/21/17.
 */

public class DefaultDataJsonManager {

    protected static String fileName = "default_data";

    public static void jsonWriter(Context context, Default data) {
        String json = "{" +
                "\"vo\":" + data.getV0Depart() + "," +
                "\"xs\":" + data.getxStation() + "," +
                "\"ys\":" + data.getyStation() +
                "}";
        JsonManager.jsonWriter(context, json, DefaultDataJsonManager.fileName);
    }

    public static Default jsonReader(Context context) {
        Default data = new Default();
        File file = new File(PathManager.getRootPath(context), DefaultDataJsonManager.fileName + ".json");

        try {
            String json = JsonManager.jsonReader(context, file);

            if(json == "")
                return data;

            JSONObject obj = new JSONObject(json);

            data.setV0Depart(obj.getDouble("vo"));
            data.setxStation(obj.getDouble("xs"));
            data.setyStation(obj.getDouble("ys"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}

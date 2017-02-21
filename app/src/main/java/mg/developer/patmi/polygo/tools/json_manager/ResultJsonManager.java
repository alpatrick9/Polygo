package mg.developer.patmi.polygo.tools.json_manager;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mg.developer.patmi.polygo.models.bean.Result;
import mg.developer.patmi.polygo.models.entity.Data;
import mg.developer.patmi.polygo.tools.PathManager;

/**
 * Created by developer on 2/21/17.
 */

public class ResultJsonManager {

    protected static String fileName = "results_data";

    public static void jsonWriter(Context context, List<Result> results) {
        String json = ResultJsonManager.stringifyResultList(results);

        JsonManager.jsonWriter(context,json,ResultJsonManager.fileName);
    }

    protected static String stringifyResultList(List<Result> results) {
        String json = "[";

        for (Result r : results) {
            json += ResultJsonManager.stringifyResult(r) + ",";
        }
        if(results.size() > 0)
            json = json.substring(0, json.length()-1);
        json += "]";

        return json;
    }

    protected static String stringifyResult(Result result) {
        String json = "{" +
                "\"dh\":" + result.getdH() + "," +
                "\"gisement\":" + result.getGisement() + "," +
                "\"xm\":" + result.getxM() + "," +
                "\"ym\":" + result.getyM() + "," +
                "\"data\":" + ResultJsonManager.stringifyData(result.getData()) +
                "}";
        return json;
    }

    protected static String stringifyData(Data data) {
        String json = "{" +
                "\"id\":" + data.getId() + "," +
                "\"stations\":" + data.getStations() + "," +
                "\"pv\":" + data.getpV() + "," +
                "\"hz\":" + data.gethZ() + "," +
                "\"v\":" + data.getV() + "," +
                "\"di\":" + data.getDi() +
                "}";
        return json;
    }

    public static List<Result> jsonReader(Context context) {
        List<Result> results = new ArrayList<>();
        File file = new File(PathManager.getRootPath(context), ResultJsonManager.fileName+".json");
        try {
            String json = JsonManager.jsonReader(context, file);
            if(json == "")
                return results;

            JSONArray array = new JSONArray(json);

            for(int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                Result result = new Result();

                result.setdH(object.getDouble("dh"));
                result.setGisement(object.getDouble("gisement"));
                result.setxM(object.getDouble("xm"));
                result.setyM(object.getDouble("ym"));

                JSONObject dataObject = object.getJSONObject("data");
                Data data = new Data();
                data.setId(dataObject.getLong("id"));
                data.setStations(dataObject.getString("stations"));
                data.setpV(dataObject.getString("pv"));
                data.sethZ(dataObject.getDouble("hz"));
                data.setV(dataObject.getDouble("v"));
                data.setDi(dataObject.getDouble("di"));

                result.setData(data);

                results.add(result);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return results;
    }
}

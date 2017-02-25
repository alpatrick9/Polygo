package mg.developer.patmi.polygo.tools.bean_manager;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import mg.developer.patmi.polygo.models.bean.Result;
import mg.developer.patmi.polygo.models.entity.Data;
import mg.developer.patmi.polygo.tools.Converter;
import mg.developer.patmi.polygo.tools.json_manager.JsonManager;
import mg.developer.patmi.polygo.tools.json_manager.ResultJsonManager;

/**
 * Created by developer on 2/21/17.
 */

public class ResultManager {

    public static void addResult(Context context,Result result) {
        List<Result> results = ResultManager.getResults(context);
        results.add(result);
        ResultJsonManager.jsonWriter(context, results);
    }

    public static  List<Result> getResults(Context context) {
        return ResultJsonManager.jsonReader(context);
    }

    public static void resetResults(Context context) {
        List<Result> results = new ArrayList<>();
        ResultJsonManager.jsonWriter(context, results);
    }

    public static Result createNewResult(Context context, Data data) {
        Result result = new Result();
        return calulResult(context, data, result);
    }

    public static void updateResult(Context context, Data data) {
        List<Result> results = getResults(context);
        if(results.isEmpty())
            return;
        for(int i = 0; i < results.size(); i++) {
            Result result = results.get(i);
            if(result.getData().getId() == data.getId()) {
                result = calulResult(context, data, result);
                results.set(i, result);
                ResultJsonManager.jsonWriter(context,results);
                break;
            }
        }
    }

    public static void deleteResult(Context context, Data data) {
        List<Result> results = getResults(context);
        if(results.isEmpty())
            return;
        for(int i = 0; i < results.size(); i++) {
            Result result = results.get(i);
            if(result.getData().getId() == data.getId()) {
                results.remove(i);
                ResultJsonManager.jsonWriter(context, results);
            }
        }
    }

    public static void deleteAllResult(Context context) {
        List<Result> results = new ArrayList<>();
        ResultJsonManager.jsonWriter(context, results);
    }

    private static Result calulResult(Context context, final Data data, final Result result) {

        result.setData(data);

        result.setdH(ResultManager.dHFunction(data));

        result.setGisement(ResultManager.gisementFunction(context, data));

        result.setxM(ResultManager.xmFunction(context, result));

        result.setyM(ResultManager.ymFunction(context, result));

        return result;
    }

    protected static Double dHFunction(Data data) {
        Double dh = data.getDi() * Math.pow(
                Math.sin(
                        Converter.gradeToRadian(data.getV())
                ), 2);
        return Converter.decimalFormat(dh,2);
    }

    protected static Double gisementFunction(Context context, Data data) {
        Double alpha = DefaultDataManager.getDefaultData(context).getV0Depart()  + data.gethZ();
        if(alpha > 400)
            return Converter.decimalFormat(alpha - 400,4);
        return Converter.decimalFormat(alpha,4);
    }

    protected static Double xmFunction(Context context, Result result) {
        Double xm = DefaultDataManager.getDefaultData(context).getxStation() +
                result.getdH() * Math.sin(
                        Converter.gradeToRadian(result.getGisement())
                );
        return Converter.decimalFormat(xm, 2);
    }

    protected static Double ymFunction(Context context, Result result) {
        Double ym = DefaultDataManager.getDefaultData(context).getxStation() +
                result.getdH() * Math.cos(
                        Converter.gradeToRadian(result.getGisement())
                );
        return Converter.decimalFormat(ym, 2);
    }
}

package mg.developer.patmi.polygo.tools.bean_manager;

import java.util.ArrayList;
import java.util.List;

import mg.developer.patmi.polygo.models.bean.Result;
import mg.developer.patmi.polygo.models.entity.Data;
import mg.developer.patmi.polygo.tools.Converter;

/**
 * Created by developer on 2/21/17.
 */

public class ResultManager {

    protected static List<Result> results = new ArrayList<>();

    public static void addResult(Result result) {
        ResultManager.results.add(result);
    }

    public static  List<Result> getResults() {
        return ResultManager.results;
    }

    public static void resetResults() {
        ResultManager.results = new ArrayList<>();
    }

    public static Result calculResult(Data data) {
        Result result = new Result();

        result.setData(data);

        result.setdH(ResultManager.dHFunction(data));

        result.setGisement(ResultManager.gisementFunction(data));

        result.setxM(ResultManager.xmFunction(result));

        result.setyM(ResultManager.ymFunction(result));

        return result;
    }

    protected static Double dHFunction(Data data) {
        Double dh = data.getDi() * Math.pow(
                Math.sin(
                        Converter.gradeToRadian(data.getV())
                ), 2);
        return Converter.decimalFormat(dh,2);
    }

    protected static Double gisementFunction(Data data) {
        Double alpha = DefaultDataManager.getDefaultData().getV0Depart()  + data.gethZ();
        if(alpha > 400)
            return Converter.decimalFormat(alpha - 400,4);
        return Converter.decimalFormat(alpha,4);
    }

    protected static Double xmFunction(Result result) {
        Double xm = DefaultDataManager.getDefaultData().getxStation() +
                result.getdH() * Math.sin(
                        Converter.gradeToRadian(result.getGisement())
                );
        return Converter.decimalFormat(xm, 2);
    }

    protected static Double ymFunction(Result result) {
        Double ym = DefaultDataManager.getDefaultData().getxStation() +
                result.getdH() * Math.cos(
                        Converter.gradeToRadian(result.getGisement())
                );
        return Converter.decimalFormat(ym, 2);
    }
}

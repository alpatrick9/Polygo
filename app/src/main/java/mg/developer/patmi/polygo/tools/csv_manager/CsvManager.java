package mg.developer.patmi.polygo.tools.csv_manager;

import android.content.Context;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;
import mg.developer.patmi.polygo.R;
import mg.developer.patmi.polygo.models.bean.Result;
import mg.developer.patmi.polygo.tools.PathManager;
import mg.developer.patmi.polygo.tools.json_manager.ResultJsonManager;

/**
 * Created by patmi on 25/02/2017.
 */
public class CsvManager {

    public static void export(Context context, List<Result> results) {
        final String path = PathManager.getExternalStorageDir();
        File dir = new File(path);
        Boolean success = true;

        if (!dir.exists()) {
            success = dir.mkdir(); //On cree le repertoire (s'il n'existe pas!!)
        }

        if (success) {
            File file = new File(path, "poly_data.csv");
            String[] header = {
                    context.getResources().getString(R.string.station_placeholder),
                    context.getResources().getString(R.string.pv_placeholder),
                    context.getResources().getString(R.string.hz_placeholder),
                    context.getResources().getString(R.string.v_placeholder),
                    context.getResources().getString(R.string.di_placeholder),
                    context.getResources().getString(R.string.dh_title),
                    context.getResources().getString(R.string.gisement_title),
                    context.getResources().getString(R.string.xm_title),
                    context.getResources().getString(R.string.ym_title)
            };
            try {
                file.createNewFile();
                CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
                csvWriter.writeNext(header);
                for (Result result : results) {
                    String[] array = {
                            result.getData().getStations(),
                            result.getData().getpV(),
                            result.getData().gethZ().toString(),
                            result.getData().getV().toString(),
                            result.getData().getDi().toString(),
                            result.getdH().toString(),
                            result.getGisement().toString(),
                            result.getxM().toString(),
                            result.getyM().toString()
                    };
                    csvWriter.writeNext(array);
                }
                csvWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void excelExport(Context context, List<Result> results) {
        final String path = PathManager.getExternalStorageDir();
        File dir = new File(path);
        Boolean success = true;

        String[] header = {
                context.getResources().getString(R.string.station_placeholder),
                context.getResources().getString(R.string.pv_placeholder),
                context.getResources().getString(R.string.hz_placeholder),
                context.getResources().getString(R.string.v_placeholder),
                context.getResources().getString(R.string.di_placeholder),
                context.getResources().getString(R.string.dh_title),
                context.getResources().getString(R.string.gisement_title),
                context.getResources().getString(R.string.xm_title),
                context.getResources().getString(R.string.ym_title)
        };

        if (!dir.exists()) {
            success = dir.mkdir(); //On cree le repertoire (s'il n'existe pas!!)
        }

        if (success) {
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet();
            for (int ligne = 0; ligne <= results.size(); ligne++) {

                HSSFRow row = sheet.createRow(ligne);
                if (ligne == 0) {
                    for (int col = 0; col < header.length; col++) {
                        HSSFCell cell = row.createCell(col);
                        cell.setCellValue(header[col]);
                    }
                } else {

                    HSSFCell stationCell = row.createCell(0);
                    stationCell.setCellValue(results.get(ligne - 1).getData().getStations());

                    HSSFCell pvCell = row.createCell(1);
                    pvCell.setCellValue(results.get(ligne - 1).getData().getpV());

                    HSSFCell hzCell = row.createCell(2);
                    hzCell.setCellValue(results.get(ligne - 1).getData().gethZ());

                    HSSFCell vCell = row.createCell(3);
                    vCell.setCellValue(results.get(ligne - 1).getData().getV());

                    HSSFCell diCell = row.createCell(4);
                    diCell.setCellValue(results.get(ligne - 1).getData().getDi());

                    HSSFCell dhCell = row.createCell(5);
                    dhCell.setCellValue(results.get(ligne - 1).getdH());

                    HSSFCell gisementCell = row.createCell(6);
                    gisementCell.setCellValue(results.get(ligne - 1).getGisement());

                    HSSFCell xmCell = row.createCell(7);
                    xmCell.setCellValue(results.get(ligne - 1).getxM());

                    HSSFCell ymCell = row.createCell(8);
                    ymCell.setCellValue(results.get(ligne - 1).getyM());
                }

            }
            try {
                FileOutputStream fileOut = new FileOutputStream(path + "polygo.xls");
                hwb.write(fileOut);
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

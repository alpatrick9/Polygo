package mg.developer.patmi.polygo.tools.dialog_manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import mg.developer.patmi.polygo.R;
import mg.developer.patmi.polygo.dao.DataDao;
import mg.developer.patmi.polygo.models.bean.Default;
import mg.developer.patmi.polygo.models.bean.Result;
import mg.developer.patmi.polygo.models.entity.Data;
import mg.developer.patmi.polygo.tools.bean_manager.DefaultDataManager;
import mg.developer.patmi.polygo.tools.bean_manager.ResultManager;

/**
 * Created by developer on 2/21/17.
 */

public class DialogDefaultDataManager {

    public static void dialogChangeDefaultData(final Context context) {
        Default data = DefaultDataManager.getDefaultData(context);
        createDialog(context, data);
    }

    protected static void createDialog(final Context context, final Default data) {

        final Handler handler = new Handler();
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.change_default_data_layout, null);

        final EditText vo = (EditText) dialogView.findViewById(R.id.v0EditText);
        final EditText x = (EditText) dialogView.findViewById(R.id.xStationEditText);
        final EditText y = (EditText) dialogView.findViewById(R.id.yStationEditText);

        vo.setText(data.getV0Depart() == null ? null : data.getV0Depart().toString());
        x.setText(data.getxStation() == null ? null : data.getxStation().toString());
        y.setText(data.getyStation() == null ? null : data.getyStation().toString());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        // set title
        alertDialogBuilder.setTitle(context.getResources().getString(R.string.save_default_data_title));

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int id) {

                        data.setV0Depart(vo.getText().toString().isEmpty() ? null : Double.parseDouble(vo.getText().toString()));
                        data.setxStation(x.getText().toString().isEmpty() ? null : Double.parseDouble(x.getText().toString()));
                        data.setyStation(y.getText().toString().isEmpty() ? null : Double.parseDouble(y.getText().toString()));

                        final boolean isDataValid =
                                !(data.getV0Depart() == null) &&
                                        !(data.getxStation() == null) &&
                                        !(data.getyStation() == null);

                        if (!isDataValid) {
                            Toast.makeText(context, context.getString(R.string.invalid_data), Toast.LENGTH_SHORT).show();
                            createDialog(context, data);
                        } else {
                            final Runnable run = new Runnable() {
                                @Override
                                public void run() {
                                    ((Activity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(context, ((Activity) context).getResources().getString(R.string.info_default_data), Toast.LENGTH_SHORT).show();
                                            DataDao dataDao = new DataDao(context);
                                            try {
                                                List<Data> datas = dataDao.findAll();
                                                ResultManager.resetResults(context);
                                                for(Data d: datas) {
                                                    Result r = ResultManager.calculResult(context, d);
                                                    ResultManager.addResult(context,r);
                                                }
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }

                                            dialog.cancel();
                                        }
                                    });
                                }
                            };
                            new Thread() {
                                @Override
                                public void run() {
                                    DefaultDataManager.setDefaultData(context,data);
                                    handler.post(run);
                                }
                            }.start();
                        }
                        dialog.cancel();
                    }
                });

        // create alert dialog
        //AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialogBuilder.setView(dialogView);
        // show it0
        alertDialogBuilder.show();
    }
}

package mg.developer.patmi.polygo.tools.dialog_manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

import mg.developer.patmi.polygo.R;
import mg.developer.patmi.polygo.dao.DataDao;
import mg.developer.patmi.polygo.fragment.DataFragment;
import mg.developer.patmi.polygo.models.bean.Result;
import mg.developer.patmi.polygo.models.entity.Data;
import mg.developer.patmi.polygo.tools.Tools;
import mg.developer.patmi.polygo.tools.bean_manager.ResultManager;

/**
 * Created by patmi on 12/02/2017.
 */
public class DialogDataManager {

    private Context context;
    private DataDao dataDao;

    private boolean isInsert = false;

    public DialogDataManager(Context context) {
        this.context = context;
        this.dataDao = new DataDao(this.context);
    }

    public void dialogSaveData() {
        Data data = new Data();
        dialogSaveUpdateData(data, "save");
    }

    public void dialogUpdateData(Long id) {
        Data data = dataDao.findBy(id);
        if (data.getId() != null) {
            dialogSaveUpdateData(data, "update");
        }
    }

    protected void dialogSaveUpdateData(final Data data, final String action) {
        final Handler handler = new Handler();
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.save_data_layout, null);

        final EditText stationEditText = (EditText) dialogView.findViewById(R.id.stationEditText);
        final EditText pvEditText = (EditText) dialogView.findViewById(R.id.pvEditText);
        final EditText hzEditText = (EditText) dialogView.findViewById(R.id.hzEditText);
        final EditText vEditText = (EditText) dialogView.findViewById(R.id.vEditText);
        final EditText diEditText = (EditText) dialogView.findViewById(R.id.diEditText);

        stationEditText.setText(data.getStations());
        pvEditText.setText(data.getpV());
        hzEditText.setText(data.gethZ() == null ? null : data.gethZ().toString());
        vEditText.setText(data.getV() == null ? null : data.getV().toString());
        diEditText.setText(data.getDi() == null ? null : data.getDi().toString());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        // set title
        alertDialogBuilder.setTitle(context.getResources().getString(R.string.save_title));
        if (action.equals("update")) {
            alertDialogBuilder.setTitle(context.getResources().getString(R.string.update_title, data.getStations()));
        }

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        data.setStations(stationEditText.getText().toString());
                        data.setpV(pvEditText.getText().toString());
                        data.sethZ(hzEditText.getText().toString().isEmpty() ? null : Double.parseDouble(hzEditText.getText().toString()));
                        data.setV(vEditText.getText().toString().isEmpty() ? null : Double.parseDouble(vEditText.getText().toString()));
                        data.setDi(diEditText.getText().toString().isEmpty() ? null : Double.parseDouble(diEditText.getText().toString()));

                        final boolean isDataValid =
                                !data.getStations().isEmpty() &&
                                        !data.getpV().isEmpty() &&
                                        !(data.gethZ() == null) &&
                                        !(data.getV() == null) &&
                                        !(data.getDi() == null);

                        if (!isDataValid) {
                            Toast.makeText(context, context.getString(R.string.invalid_data), Toast.LENGTH_SHORT).show();
                            dialogSaveUpdateData(data, action);
                        } else {
                            final Runnable run = new Runnable() {
                                @Override
                                public void run() {
                                    ((Activity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (isInsert) {
                                                Fragment fragment = new DataFragment();
                                                Tools.replaceFragment(context, fragment);
                                            } else
                                                Toast.makeText(context, ((Activity) context).getResources().getString(R.string.erreur), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            };
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        switch (action) {
                                            case "save":
                                                final Data dataDb = dataDao.create(data);
                                                isInsert = dataDb.getId() != null;
                                                if(isInsert) {
                                                    Result result = ResultManager.calculResult(context, dataDb);
                                                    ResultManager.addResult(context, result);
                                                }
                                                break;
                                            case "update":
                                                isInsert = dataDao.update(data) == 1;
                                                break;
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    handler.post(run);
                                }
                            }.start();
                        }
                        dialog.cancel();
                    }
                }).setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // if this button is clicked, just close
                // the dialog box and do nothing
                dialog.cancel();
            }
        });

        // create alert dialog
        //AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialogBuilder.setView(dialogView);
        // show it0
        alertDialogBuilder.show();
    }

    public void dialogDataDelete(final Data data) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        // set title
        alertDialogBuilder.setTitle(context.getResources().getString(R.string.app_name));
        // set dialog message
        alertDialogBuilder
                .setMessage(context.getResources().getString(R.string.message_confirm_del, data.getStations()))
                .setCancelable(false)
                .setPositiveButton(context.getResources().getString(R.string.delete_label), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            dataDao.delete(data);
                            Fragment fragment = new DataFragment();
                            Tools.replaceFragment(context, fragment);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void dialogDataDeleteAll() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        // set title
        alertDialogBuilder.setTitle(context.getResources().getString(R.string.app_name));
        // set dialog message
        alertDialogBuilder
                .setMessage(context.getResources().getString(R.string.message_confirm_del_all))
                .setCancelable(false)
                .setPositiveButton(context.getResources().getString(R.string.delete_label), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            dataDao.deleteAll();
                            // ajouter la suppression de tous les resultats
                            Fragment fragment = new DataFragment();
                            Tools.replaceFragment(context, fragment);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}

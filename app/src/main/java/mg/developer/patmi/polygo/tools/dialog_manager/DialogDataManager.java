package mg.developer.patmi.polygo.tools.dialog_manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

        final TextView dialogTitle = (TextView) dialogView.findViewById(R.id.dialogTitle);

        stationEditText.setText(data.getStations());
        pvEditText.setText(data.getpV());
        hzEditText.setText(data.gethZ() == null ? null : data.gethZ().toString());
        vEditText.setText(data.getV() == null ? null : data.getV().toString());
        diEditText.setText(data.getDi() == null ? null : data.getDi().toString());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        // set title
        dialogTitle.setText(context.getResources().getString(R.string.save_title));
        if (action.equals("update")) {
            dialogTitle.setText(context.getResources().getString(R.string.update_title, data.getStations()));
        }

        // set dialog message
        alertDialogBuilder
                .setCancelable(false);
        // create alert dialog
        //AlertDialog alertDialog = alertDialogBuilder.create();
        final AlertDialog dialog = alertDialogBuilder.setView(dialogView).create();
        dialog.show();

        Button addButton = (Button)dialogView.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                            Result result = ResultManager.createNewResult(context, dataDb);
                                            ResultManager.addResult(context, result);
                                        }
                                        break;
                                    case "update":
                                        isInsert = dataDao.update(data) == 1;
                                        if(isInsert) {
                                            ResultManager.updateResult(context, data);
                                        }
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
        });

        Button cancelButton = (Button) dialogView.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    public void dialogDataDelete(final Data data) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_info_layout, null);

        TextView titleTextView = (TextView)dialogView.findViewById(R.id.title);
        titleTextView.setText(context.getResources().getString(R.string.information));

        TextView messageTextView = (TextView)dialogView.findViewById(R.id.info);
        messageTextView.setText(context.getResources().getString(R.string.message_confirm_del, data.getStations()));

        Button okButton = (Button) dialogView.findViewById(R.id.okButton);
        Button cancelButton = (Button) dialogView.findViewById(R.id.cancelButton);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        // set dialog message
        alertDialogBuilder
                .setCancelable(false);
        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.setView(dialogView).create();
        alertDialog.show();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int i = dataDao.delete(data);
                    if(i == 0)
                        return;
                    ResultManager.deleteResult(context, data);
                    Fragment fragment = new DataFragment();
                    Tools.replaceFragment(context, fragment);
                    alertDialog.cancel();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }

    public void dialogDataDeleteAll() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_info_layout, null);

        TextView titleTextView = (TextView)dialogView.findViewById(R.id.title);
        titleTextView.setText(context.getResources().getString(R.string.information));

        TextView messageTextView = (TextView)dialogView.findViewById(R.id.info);
        messageTextView.setText(context.getResources().getString(R.string.message_confirm_del_all));

        Button okButton = (Button) dialogView.findViewById(R.id.okButton);
        Button cancelButton = (Button) dialogView.findViewById(R.id.cancelButton);
        // set dialog message
        alertDialogBuilder
                .setCancelable(false);

        final AlertDialog alertDialog = alertDialogBuilder.setView(dialogView).create();
        alertDialog.show();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dataDao.deleteAll();
                    ResultManager.deleteAllResult(context);
                    Fragment fragment = new DataFragment();
                    Tools.replaceFragment(context, fragment);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }
}

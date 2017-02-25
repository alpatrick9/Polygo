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
import mg.developer.patmi.polygo.fragment.ResultFragment;
import mg.developer.patmi.polygo.models.bean.Default;
import mg.developer.patmi.polygo.models.bean.Result;
import mg.developer.patmi.polygo.models.entity.Data;
import mg.developer.patmi.polygo.tools.Tools;
import mg.developer.patmi.polygo.tools.bean_manager.DefaultDataManager;
import mg.developer.patmi.polygo.tools.bean_manager.ResultManager;

/**
 * Created by patmi on 25/02/2017.
 */
public class DialogResultManager {

    public static void showDetailsDialog(final Context context, final Result result) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.show_detail_result, null);

        final EditText stationEditText = (EditText) dialogView.findViewById(R.id.stationEditText);
        final EditText pvEditText = (EditText) dialogView.findViewById(R.id.pvEditText);
        final EditText hzEditText = (EditText) dialogView.findViewById(R.id.hzEditText);
        final EditText vEditText = (EditText) dialogView.findViewById(R.id.vEditText);
        final EditText diEditText = (EditText) dialogView.findViewById(R.id.diEditText);

        stationEditText.setText(result.getData().getStations());
        pvEditText.setText(result.getData().getpV());
        hzEditText.setText(result.getData().gethZ() == null ? null : result.getData().gethZ().toString());
        vEditText.setText(result.getData().getV() == null ? null : result.getData().getV().toString());
        diEditText.setText(result.getData().getDi() == null ? null : result.getData().getDi().toString());

        final EditText dh = (EditText) dialogView.findViewById(R.id.dh);
        final EditText gisement = (EditText) dialogView.findViewById(R.id.gisement);
        final EditText xm = (EditText) dialogView.findViewById(R.id.xm);
        final EditText ym = (EditText) dialogView.findViewById(R.id.ym);

        dh.setText(result.getdH() == null ? null : result.getdH().toString());
        gisement.setText(result.getGisement() == null ? null : result.getGisement().toString());
        xm.setText(result.getxM() == null ? null : result.getxM().toString());
        ym.setText(result.getyM() == null ? null : result.getyM().toString());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        // set title
        alertDialogBuilder.setTitle(context.getResources().getString(R.string.details));

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int id) {
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

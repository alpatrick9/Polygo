package mg.developer.patmi.polygo.tools.json_manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import mg.developer.patmi.polygo.tools.PathManager;

/**
 * Created by developer on 2/21/17.
 */

public class JsonManager {

    public static void jsonWriter(Context context, String json, String nameFile) {
        File dir = new File(PathManager.getRootPath(context));

        Boolean success = true;

        if (!dir.exists()) {
            success = dir.mkdir(); //On cree le repertoire (s'il n'existe pas!!)
        }

        File file = new File(PathManager.getRootPath(context), nameFile + ".json");
        if (success) {
            try {
                FileOutputStream output = new FileOutputStream(file, false); //le true est pour ecrire en fin de fichier, et non l'ecraser et false pour ecraser
                output.write(json.getBytes());
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("Error", "ERROR DE CREATION DE DOSSIER");
        }
    }

    public static String jsonReader(Context context, File file) {
        String json = "";

        if (!file.exists()) {
            return json;
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            StringBuffer fileContent = new StringBuffer("");
            byte[] buffer = new byte[1024];
            int line;
            while ((line = fileInputStream.read(buffer)) != -1) {
                fileContent.append(new String(buffer, 0, line));
            }
            json = fileContent.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}

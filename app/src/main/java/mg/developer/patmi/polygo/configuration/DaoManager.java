package mg.developer.patmi.polygo.configuration;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import mg.developer.patmi.polygo.models.entity.Data;

/**
 * Created by developer on 10/5/16.
 */

public class DaoManager {

    SqliteHelper sqliteHelper;

    Dao<Data, Long> dataDao;



    public DaoManager(Context context) {
        sqliteHelper = OpenHelperManager.getHelper(context, SqliteHelper.class);
    }

    public Dao<Data, Long> getDataDao() throws SQLException {
        if(dataDao == null)
            dataDao = sqliteHelper.getDao(Data.class);
        return dataDao;
    }

}

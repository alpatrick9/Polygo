package mg.developer.patmi.polygo.dao;

import android.content.Context;

import java.sql.SQLException;

import mg.developer.patmi.polygo.configuration.DaoManager;
import mg.developer.patmi.polygo.models.Data;

/**
 * Created by patmi on 12/02/2017.
 */
public class DataDao extends AbstractDao<Data, Long> {

    Context context;

    public DataDao(Context context) {
        this.context = context;
        try {
            this.dao = new DaoManager(context).getDataDao();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

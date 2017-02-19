package mg.developer.patmi.polygo.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by developer on 10/5/16.
 */

public class AbstractDao<E,K> {

    protected Dao<E,K> dao;

    public List<E> findAll() throws SQLException {
        return dao.queryForAll();
    }

    public E findBy(K id) {
        E result = null;
        try {
            result = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public Integer create(E s) throws SQLException {
        return dao.create(s);
    }

    public Integer delete(E s) throws SQLException {
        return dao.delete(s);
    }

    public Integer update(E s) throws SQLException {
        return dao.update(s);
    }

    public Integer countRow() throws SQLException {
        return (int)dao.countOf();
    }

    public void deleteAll() throws SQLException {
        DeleteBuilder<E, K> deleteBuilder = dao.deleteBuilder();
        deleteBuilder.delete();
    }

    public boolean isTableExist() throws SQLException {
        return dao.isTableExists();
    }
}

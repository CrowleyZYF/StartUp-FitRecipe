package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import cn.fitrecipe.android.entity.Series;
import cn.fitrecipe.android.entity.SeriesPlan;

/**
 * Created by wk on 2015/9/4.
 */
public class SeriesPlanDao {

    private Dao<SeriesPlan, Integer> seriesPlanDaoOpe;
    private DatabaseHelper helper;

    public SeriesPlanDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            seriesPlanDaoOpe = helper.getDao(SeriesPlan.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public SeriesPlan add(SeriesPlan seriesPlan) {
        try {
            if(seriesPlan.getId() != -1 && seriesPlanDaoOpe.idExists(seriesPlan.getId()))
                seriesPlanDaoOpe.update(seriesPlan);
            else
                seriesPlanDaoOpe.create(seriesPlan);
            return seriesPlan;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public SeriesPlan get(int id) {
        try {
            return seriesPlanDaoOpe.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SeriesPlan> getUsedPlans() {
        try {
            return seriesPlanDaoOpe.queryForEq("isUsed", true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SeriesPlan> getAll() {
        try {
            PreparedQuery<SeriesPlan> preparedQuery = seriesPlanDaoOpe.queryBuilder().where().lt("id", 10000).prepare();
            return seriesPlanDaoOpe.query(preparedQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getMaxId() {
        List<SeriesPlan> plans = null;
        try {
            plans = seriesPlanDaoOpe.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(plans.size() == 0)
            return 0;
        else {
            Collections.sort(plans);
            return plans.get(plans.size() - 1).getId();
        }
    }
}

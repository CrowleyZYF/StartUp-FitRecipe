package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
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

    private Dao<SeriesPlan, String> seriesPlanDaoOpe;
    private DatabaseHelper helper;

    public SeriesPlanDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            seriesPlanDaoOpe = helper.getDao(SeriesPlan.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(SeriesPlan seriesPlan) {
        try {
            seriesPlanDaoOpe.createOrUpdate(seriesPlan);
            if(!seriesPlan.getTitle().equals("personal plan")) {
                DeleteBuilder<SeriesPlan, String> builder = seriesPlanDaoOpe.deleteBuilder();
                builder.where().lt("joined_date", seriesPlan.getJoined_date());
                builder.delete();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

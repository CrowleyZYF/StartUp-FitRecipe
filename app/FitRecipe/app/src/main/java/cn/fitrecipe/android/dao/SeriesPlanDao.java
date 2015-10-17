package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.fitrecipe.android.entity.Series;
import cn.fitrecipe.android.entity.SeriesPlan;
import cn.fitrecipe.android.function.Common;

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

    public List<SeriesPlan> get(String start, String end) {
        try {
            List<SeriesPlan> plans = new ArrayList<>();
            QueryBuilder<SeriesPlan, String> builder = seriesPlanDaoOpe.queryBuilder();
            builder.where().le("joined_date", start);
            builder.orderBy("joined_date", false);
            builder.limit(1L);
            plans.addAll(builder.query());
            if(Common.getDiff(end, start) > 0) {
                QueryBuilder<SeriesPlan, String> builder1 = seriesPlanDaoOpe.queryBuilder();
                builder1.where().le("joined_date", end);
                builder1.where().ge("joined_date", Common.getSomeDay(start, 1));
                builder1.orderBy("joined_date", false);
                plans.addAll(builder1.query());
            }
            return plans;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

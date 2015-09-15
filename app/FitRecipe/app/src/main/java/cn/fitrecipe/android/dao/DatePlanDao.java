package cn.fitrecipe.android.dao;

import android.content.Context;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;
import java.util.List;

import cn.fitrecipe.android.entity.DatePlan;

/**
 * Created by wk on 2015/9/14.
 */
public class DatePlanDao {
    private Dao<DatePlan, String> datePlanDaoOpe;
    private DatabaseHelper helper;

    public DatePlanDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            datePlanDaoOpe = helper.getDao(DatePlan.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DatePlan> getById(String start, String end) {
        try {
            PreparedQuery<DatePlan> preparedQuery = datePlanDaoOpe.queryBuilder().where().ge("date", start).and().le("date", end).prepare();
            return datePlanDaoOpe.query(preparedQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addDatePlan(DatePlan datePlan) {
        try {
            datePlanDaoOpe.createOrUpdate(datePlan);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

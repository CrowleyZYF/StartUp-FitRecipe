package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import cn.fitrecipe.android.entity.DayPlan;

/**
 * Created by wk on 2015/9/1.
 */
public class DayPlanDao {
    private Dao<DayPlan, Integer> dayPlanDaoOpe;
    private DatabaseHelper helper;

    public DayPlanDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            dayPlanDaoOpe = helper.getDao(DayPlan.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int add(DayPlan dayPlan) {
        try {
            if(dayPlanDaoOpe.idExists(dayPlan.getId())) {
                dayPlanDaoOpe.update(dayPlan);
                return dayPlan.getId();
            }else {
                dayPlanDaoOpe.create(dayPlan);
                return dayPlan.getId();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DayPlan get(String date) {
        try {
            List<DayPlan> dayPlans = dayPlanDaoOpe.queryForEq("date", date);
            if(dayPlans != null && dayPlans.size() > 0)
                return dayPlans.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

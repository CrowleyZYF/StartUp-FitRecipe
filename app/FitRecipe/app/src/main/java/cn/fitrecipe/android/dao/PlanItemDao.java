package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import cn.fitrecipe.android.entity.PlanItem;

/**
 * Created by wk on 2015/8/29.
 */
public class PlanItemDao {
    private Dao<PlanItem, Integer> planItemDaoOpe;
    private DatabaseHelper helper;

    public PlanItemDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            planItemDaoOpe = helper.getDao(PlanItem.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int add(PlanItem planItem) {
        try {
            if(planItem.getId() > 0) {
                planItemDaoOpe.update(planItem);
                return planItem.getId();
            }
            else {
                planItemDaoOpe.create(planItem);
                return planItem.getId();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PlanItem get(int id) {
        try {
            return planItemDaoOpe.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PlanItem> getPlanItemsInDayPlan(int dayPlan_id) {
        try {
            return planItemDaoOpe.queryForEq("dayPlan_id", dayPlan_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

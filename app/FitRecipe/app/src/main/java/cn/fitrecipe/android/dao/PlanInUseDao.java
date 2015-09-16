package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import cn.fitrecipe.android.entity.PlanInUse;

/**
 * Created by wk on 2015/9/15.
 */
public class PlanInUseDao {

    private Dao<PlanInUse, Integer> planInUseDaoOpe;
    private DatabaseHelper helper;

    public PlanInUseDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            planInUseDaoOpe = helper.getDao(PlanInUse.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PlanInUse getPlanInUse() {
        try {
            return planInUseDaoOpe.queryForId(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPlanInUse(PlanInUse planInUse) {
        planInUse.setIsUsed(1);
        try {
            planInUseDaoOpe.createOrUpdate(planInUse);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPlanNotInUse() {
        try {
            planInUseDaoOpe.deleteById(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import cn.fitrecipe.android.entity.MyPlan;


/**
 * Created by wk on 2015/9/8.
 */
public class MyPlanDao {

    private Dao<MyPlan, String> myPlanDaoDaoOpe;
    private DatabaseHelper helper;

    public MyPlanDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            myPlanDaoDaoOpe = helper.getDao(MyPlan.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(MyPlan plan) {
        try {
            if(plan.getStartDate() != null && myPlanDaoDaoOpe.idExists(plan.getStartDate()))
                myPlanDaoDaoOpe.update(plan);
            else
                myPlanDaoDaoOpe.create(plan);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MyPlan> get() {
        try {
            return myPlanDaoDaoOpe.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

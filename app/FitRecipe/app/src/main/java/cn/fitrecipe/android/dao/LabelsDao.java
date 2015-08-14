package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import cn.fitrecipe.android.entity.Labels;

/**
 * Created by wk on 2015/8/12.
 */
public class LabelsDao {

    private Dao<Labels, Integer> labelsDaoOpe;
    private FrDbHelper helper;

    public LabelsDao(Context context) {
        try {
            helper = FrDbHelper.getHelper(context);
            labelsDaoOpe = helper.getDao(Labels.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addLabels(Labels labels) {
        try {
            labelsDaoOpe.create(labels);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Labels getLabels(int id) {
        Labels labels = null;
        try {
            labels = labelsDaoOpe.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return labels;
    }
}

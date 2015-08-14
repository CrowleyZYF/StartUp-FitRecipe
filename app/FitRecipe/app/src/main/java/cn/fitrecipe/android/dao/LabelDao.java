package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import cn.fitrecipe.android.entity.Label;

/**
 * Created by wk on 2015/8/12.
 */
public class LabelDao {

    private Dao<Label, Integer> labelDaoOpe;
    private FrDbHelper helper;

    public LabelDao(Context context) {
        try {
            helper = FrDbHelper.getHelper(context);
            labelDaoOpe = helper.getDao(Label.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Label label) {
        try {
            labelDaoOpe.create(label);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Label get(int id) {
        Label label = null;
        try {
            label = labelDaoOpe.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return label;
    }

}

package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import cn.fitrecipe.android.entity.Component;


/**
 * Created by wk on 2015/8/14.
 */
public class ComponentDao {

    private Dao<Component, Integer> componentDaoOpe;
    private DatabaseHelper helper;

    public ComponentDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            componentDaoOpe = helper.getDao(Component.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int add(Component component) {
        int id = 0;
        try {
            id = componentDaoOpe.create(component);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public List<Component> getComponents(int recipe_id) {
        List<Component> components = null;
        try {
            components = componentDaoOpe.queryForEq("recipe_id", recipe_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }

    public Component get(int id) {
        Component component = null;
        try {
            component = componentDaoOpe.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return component;
    }

}

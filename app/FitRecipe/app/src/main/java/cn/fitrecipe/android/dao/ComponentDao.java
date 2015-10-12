package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.entity.Component;
import cn.fitrecipe.android.entity.Nutrition;


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


    public List<Component> getComponents(int recipe_id) {
        List<Component> components = null;
        try {
            components = componentDaoOpe.queryForEq("recipe_id", recipe_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }

    public List<Component> getComponentsInPlanItem(int planitem_id) {
        List<Component> components = null;
        try {
            components = componentDaoOpe.queryForEq("planitem_id", planitem_id);
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

    public void remove(int id) {
        try {
            componentDaoOpe.deleteById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateComponentStatus(Component component) {
        try {
            UpdateBuilder<Component, Integer> builder = componentDaoOpe.updateBuilder();
            builder.where().eq("id", component.getId());
            builder.updateColumnValue("status", component.getStatus()).update();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

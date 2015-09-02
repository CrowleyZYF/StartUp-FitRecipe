package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;
import java.util.List;

import cn.fitrecipe.android.entity.PlanItem2Recipe;

/**
 * Created by wk on 2015/8/29.
 */
public class PlanItem2RecipeDao {

    private Dao<PlanItem2Recipe, Integer> daoOpe;
    private DatabaseHelper helper;

    public PlanItem2RecipeDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            daoOpe = helper.getDao(PlanItem2Recipe.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(PlanItem2Recipe match) {
        try {
            PreparedQuery<PlanItem2Recipe> preparedQuery = daoOpe.queryBuilder().where().eq("recipe_id", match.getRecipe().getId()).
                    and().eq("planItem_id", match.getPlanItem().getId()).prepare();
            List<PlanItem2Recipe> matches = daoOpe.query(preparedQuery);
            if(matches != null && matches.size() > 0) {
                match.setId(matches.get(0).getId());
            }
            daoOpe.createOrUpdate(match);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(PlanItem2Recipe match) {
        DeleteBuilder<PlanItem2Recipe, Integer> builder = daoOpe.deleteBuilder();
        try {
            builder.where().eq("recipe_id", match.getRecipe().getId()).and().eq("planItem_id", match.getPlanItem().getId());
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PlanItem2Recipe> get(int planitem_id) {
        try {
            return daoOpe.queryForEq("planItem_id", planitem_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

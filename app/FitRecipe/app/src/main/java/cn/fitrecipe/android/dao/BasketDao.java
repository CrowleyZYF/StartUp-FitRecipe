package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;
import java.util.List;

import cn.fitrecipe.android.entity.BasketItem;
import cn.fitrecipe.android.entity.Recipe;

/**
 * Created by wk on 2015/8/28.
 */
public class BasketDao {

    private Dao<BasketItem, Integer> basketItemDaoOpe;
    private DatabaseHelper helper;

    public BasketDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            basketItemDaoOpe = helper.getDao(BasketItem.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(BasketItem item) {
        try {
            basketItemDaoOpe.createOrUpdate(item);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(String type, int itemId) {
        try {
            PreparedQuery<BasketItem> query = basketItemDaoOpe.queryBuilder().where().eq("type", type).and().eq("itemId", itemId).prepare();
            basketItemDaoOpe.delete(basketItemDaoOpe.query(query));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        try {
            basketItemDaoOpe.delete(basketItemDaoOpe.queryForAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BasketItem> getAll() {
        try {
            return basketItemDaoOpe.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

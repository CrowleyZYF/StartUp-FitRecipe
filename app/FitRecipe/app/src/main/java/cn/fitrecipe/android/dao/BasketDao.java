package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import cn.fitrecipe.android.entity.Basket;

/**
 * Created by wk on 2015/8/28.
 */
public class BasketDao {

    private Dao<Basket, Integer> basketDaoOpe;
    private DatabaseHelper helper;

    public BasketDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            basketDaoOpe = helper.getDao(Basket.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Basket basket) {
        try {
            basket.setId(0);
            basketDaoOpe.createOrUpdate(basket);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Basket getBasket() {
        try {
            return basketDaoOpe.queryForId(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

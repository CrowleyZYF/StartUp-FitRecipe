package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;
import java.util.List;

import cn.fitrecipe.android.entity.BasketRecord;

/**
 * Created by wk on 2015/10/15.
 */
public class BasketRecordDao {

    private Dao<BasketRecord, Integer> daoOpe;
    private DatabaseHelper helper;

    public BasketRecordDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            daoOpe = helper.getDao(BasketRecord.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() {
        DeleteBuilder<BasketRecord, Integer> builder = daoOpe.deleteBuilder();
        try {
            builder.delete();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(String date, String type) {
        BasketRecord br = new BasketRecord();
        br.setDate(date);
        br.setType(type);
        try {
            daoOpe.create(br);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String date, String type) {
        DeleteBuilder<BasketRecord, Integer> builder = daoOpe.deleteBuilder();
        try {
            builder.where().eq("date", date);
            builder.where().eq("type", type);
            builder.delete();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BasketRecord> get(String start, String end) {
        try {
            PreparedQuery<BasketRecord> preparedQuery = daoOpe.queryBuilder().where()
                    .ge("date", start).and().le("date", end).prepare();
            return daoOpe.query(preparedQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

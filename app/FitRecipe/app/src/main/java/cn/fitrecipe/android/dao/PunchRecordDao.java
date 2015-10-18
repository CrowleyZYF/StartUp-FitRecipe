package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;
import java.util.List;

import cn.fitrecipe.android.entity.PunchRecord;

/**
 * Created by wk on 2015/10/16.
 */
public class PunchRecordDao {
    private Dao<PunchRecord, Integer> daoOpe;
    private DatabaseHelper helper;

    public PunchRecordDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            daoOpe = helper.getDao(PunchRecord.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(PunchRecord pr) {
        try {
            daoOpe.createOrUpdate(pr);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PunchRecord> get(String start, String end) {
        try {
            PreparedQuery<PunchRecord> preparedQuery = daoOpe.queryBuilder().where()
                    .ge("date", start).and().le("date", end).prepare();
            return daoOpe.query(preparedQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String date, String type) {
        DeleteBuilder<PunchRecord, Integer> builder = daoOpe.deleteBuilder();
        try {
            builder.where().eq("date", date);
            builder.where().eq("type", type);
            builder.delete();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

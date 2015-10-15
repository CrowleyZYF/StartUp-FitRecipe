package cn.fitrecipe.android.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import cn.fitrecipe.android.entity.Author;
import cn.fitrecipe.android.entity.Basket;
import cn.fitrecipe.android.entity.DatePlan;
import cn.fitrecipe.android.entity.PlanInUse;
import cn.fitrecipe.android.entity.Report;
import cn.fitrecipe.android.entity.SeriesPlan;

/**
 * Created by wk on 2015/8/11.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper{

    private static final String DB_NAME = "fr-recipe.db";
    private static DatabaseHelper instance;

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 2);
    }

    public static synchronized DatabaseHelper getHelper(Context context) {
        context = context.getApplicationContext();
        synchronized (DatabaseHelper.class) {
            if (instance == null)
                instance = new DatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Author.class);
            TableUtils.createTableIfNotExists(connectionSource, Report.class);
            TableUtils.createTableIfNotExists(connectionSource, Basket.class);
            TableUtils.createTableIfNotExists(connectionSource, SeriesPlan.class);
            TableUtils.createTableIfNotExists(connectionSource, PlanInUse.class);

        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("error", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, Author.class, true);
            TableUtils.dropTable(connectionSource, Report.class, true);
            TableUtils.dropTable(connectionSource, Basket.class, true);
            TableUtils.dropTable(connectionSource, SeriesPlan.class, true);
            TableUtils.dropTable(connectionSource, PlanInUse.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onCreate(sqLiteDatabase, connectionSource);
    }
}

package cn.fitrecipe.android.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import cn.fitrecipe.android.entity.Label;
import cn.fitrecipe.android.entity.Labels;

/**
 * Created by wk on 2015/8/11.
 */
public class FrDbHelper extends OrmLiteSqliteOpenHelper{

    private static final String DB_NAME = "fr-recipe.db";
    private static FrDbHelper instance;

    private FrDbHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public static synchronized FrDbHelper getHelper(Context context) {
//        context = context.getApplicationContext();
        if(instance == null)
            instance = new FrDbHelper(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Label.class);
            TableUtils.createTable(connectionSource, Labels.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, Label.class, true);
            TableUtils.dropTable(connectionSource, Labels.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onCreate(sqLiteDatabase, connectionSource);
    }
}

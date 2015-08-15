package cn.fitrecipe.android.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import cn.fitrecipe.android.entity.Component;
import cn.fitrecipe.android.entity.Ingredient;
import cn.fitrecipe.android.entity.Label;
import cn.fitrecipe.android.entity.Nutrition;
import cn.fitrecipe.android.entity.Recipe;

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

            TableUtils.createTableIfNotExists(connectionSource, Label.class);
            TableUtils.createTableIfNotExists(connectionSource, Ingredient.class);
            TableUtils.createTableIfNotExists(connectionSource, Component.class);
            TableUtils.createTable(connectionSource, Nutrition.class);
            TableUtils.createTable(connectionSource, Recipe.class);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("error", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, Label.class, true);
            TableUtils.dropTable(connectionSource, Ingredient.class, true);
            TableUtils.dropTable(connectionSource, Component.class, true);
            TableUtils.dropTable(connectionSource, Nutrition.class, true);
            TableUtils.dropTable(connectionSource, Recipe.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onCreate(sqLiteDatabase, connectionSource);
    }
}

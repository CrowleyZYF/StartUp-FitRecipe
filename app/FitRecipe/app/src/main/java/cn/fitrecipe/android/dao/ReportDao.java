package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import cn.fitrecipe.android.entity.Author;
import cn.fitrecipe.android.entity.Report;


/**
 * Created by wk on 2015/8/21.
 */
public class ReportDao {
    private Dao<Report, Integer> reportDaoOpe;
    private DatabaseHelper helper;

    public ReportDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            reportDaoOpe = helper.getDao(Report.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Report report) {
        try {
            reportDaoOpe.create(report);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Report getReport(Author author) {
        List<Report> reports;
        try {
            reports = reportDaoOpe.queryForEq("author_id", author.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(reports != null && reports.size() > 0)
            return reports.get(reports.size()-1);
        return null;
    }
}

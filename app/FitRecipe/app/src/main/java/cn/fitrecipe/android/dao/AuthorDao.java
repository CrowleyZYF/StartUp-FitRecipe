package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import cn.fitrecipe.android.entity.Author;

/**
 * Created by wk on 2015/8/8.
 */
public class AuthorDao {

    private Dao<Author, Integer> authorDaoOpe;
    private DatabaseHelper helper;

    public AuthorDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            authorDaoOpe = helper.getDao(Author.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Author author) {
        try {
            if(authorDaoOpe.idExists(author.getId()))
                authorDaoOpe.update(author);
            else
                authorDaoOpe.create(author);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void logout(Author author) {
        author.setIsLogin(false);
        try {
            authorDaoOpe.update(author);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Author getAuthor() {
        List<Author> authors = null;
        try {
            authors = authorDaoOpe.queryForEq("isLogin", true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(authors != null && authors.size() > 0)
            return authors.get(0);
        return null;
    }
}

package info.xudshen.jandan.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import info.xudshen.jandan.model.Article;
import info.xudshen.jandan.model.Joke;

import info.xudshen.jandan.model.ArticleDao;
import info.xudshen.jandan.model.JokeDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    public DaoSession setContext(Context context) {
        articleDao.setContext(context);
        jokeDao.setContext(context);
        return this;
    }

    private final DaoConfig articleDaoConfig;
    private final DaoConfig jokeDaoConfig;

    private final ArticleDao articleDao;
    private final JokeDao jokeDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        articleDaoConfig = daoConfigMap.get(ArticleDao.class).clone();
        articleDaoConfig.initIdentityScope(type);

        jokeDaoConfig = daoConfigMap.get(JokeDao.class).clone();
        jokeDaoConfig.initIdentityScope(type);

        articleDao = new ArticleDao(articleDaoConfig, this);
        jokeDao = new JokeDao(jokeDaoConfig, this);

        registerDao(Article.class, articleDao);
        registerDao(Joke.class, jokeDao);
    }
    
    public void clear() {
        articleDaoConfig.getIdentityScope().clear();
        jokeDaoConfig.getIdentityScope().clear();
    }

    public ArticleDao getArticleDao() {
        return articleDao;
    }

    public JokeDao getJokeDao() {
        return jokeDao;
    }

}

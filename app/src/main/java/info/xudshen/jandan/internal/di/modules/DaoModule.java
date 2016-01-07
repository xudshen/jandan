package info.xudshen.jandan.internal.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.xudshen.jandan.data.dao.ArticleDao;
import info.xudshen.jandan.data.dao.DaoSession;
import info.xudshen.jandan.data.dao.JokeDao;

/**
 * Created by xudshen on 16/1/7.
 */
@Module
public class DaoModule {
    @Provides
    @Singleton
    ArticleDao provideArticleDao(DaoSession daoSession) {
        return daoSession.getArticleDao();
    }

    @Provides
    @Singleton
    JokeDao provideJokeDao(DaoSession daoSession) {
        return daoSession.getJokeDao();
    }
}

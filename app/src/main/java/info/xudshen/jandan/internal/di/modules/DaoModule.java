package info.xudshen.jandan.internal.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.xudshen.jandan.data.dao.AuthorDao;
import info.xudshen.jandan.data.dao.DaoSession;
import info.xudshen.jandan.data.dao.PostDao;

/**
 * Created by xudshen on 16/1/7.
 * //TODO: the following code can be generated
 */
@Module
public class DaoModule {
    @Provides
    @Singleton
    PostDao providePostDao(DaoSession daoSession) {
        return daoSession.getPostDao();
    }

    @Provides
    @Singleton
    AuthorDao provideAuthorDao(DaoSession daoSession) {
        return daoSession.getAuthorDao();
    }
}

package info.xudshen.jandan.internal.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.xudshen.jandan.data.dao.AuthorDao;
import info.xudshen.jandan.data.dao.CategoryDao;
import info.xudshen.jandan.data.dao.CommentDao;
import info.xudshen.jandan.data.dao.DaoSession;
import info.xudshen.jandan.data.dao.DuoshuoCommentDao;
import info.xudshen.jandan.data.dao.MetaDao;
import info.xudshen.jandan.data.dao.PicItemDao;
import info.xudshen.jandan.data.dao.PostDao;
import info.xudshen.jandan.data.dao.SimplePostDao;

/**
 * Created by xudshen on 16/1/7.
 * //TODO: the following code can be generated
 */
@Module
public class DaoModule {
    @Provides
    @Singleton
    MetaDao provideMetaDao(DaoSession daoSession) {
        return daoSession.getMetaDao();
    }

    @Provides
    @Singleton
    PostDao providePostDao(DaoSession daoSession) {
        return daoSession.getPostDao();
    }

    @Provides
    @Singleton
    SimplePostDao provideSimplePostDao(DaoSession daoSession) {
        return daoSession.getSimplePostDao();
    }

    @Provides
    @Singleton
    AuthorDao provideAuthorDao(DaoSession daoSession) {
        return daoSession.getAuthorDao();
    }

    @Provides
    @Singleton
    CategoryDao provideCategoryDao(DaoSession daoSession) {
        return daoSession.getCategoryDao();
    }

    @Provides
    @Singleton
    CommentDao provideCommentDao(DaoSession daoSession) {
        return daoSession.getCommentDao();
    }

    @Provides
    @Singleton
    PicItemDao providePicItemDao(DaoSession daoSession) {
        return daoSession.getPicItemDao();
    }

    @Provides
    @Singleton
    DuoshuoCommentDao provideDuoshuoCommentDao(DaoSession daoSession) {
        return daoSession.getDuoshuoCommentDao();
    }
}

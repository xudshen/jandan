package info.xudshen.jandan.internal.di.components;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import info.xudshen.jandan.data.dao.AuthorDao;
import info.xudshen.jandan.data.dao.CategoryDao;
import info.xudshen.jandan.data.dao.CommentDao;
import info.xudshen.jandan.data.dao.DaoSession;
import info.xudshen.jandan.data.dao.MetaDao;
import info.xudshen.jandan.data.dao.PostDao;
import info.xudshen.jandan.data.dao.SimplePostDao;
import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.model.Category;
import info.xudshen.jandan.domain.model.Comment;
import info.xudshen.jandan.domain.repository.PostRepository;
import info.xudshen.jandan.internal.di.modules.ApplicationModule;
import info.xudshen.jandan.internal.di.modules.DaoModule;
import info.xudshen.jandan.navigation.Navigator;
import info.xudshen.jandan.view.activity.BaseActivity;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {ApplicationModule.class, DaoModule.class})
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    Context context();

    Navigator navigator();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    DaoSession daoSession();

    PostRepository postRepository();

    MetaDao metaDao();

    PostDao postDao();

    SimplePostDao simplePostDao();

    AuthorDao authorDao();

    CategoryDao categoryDao();

    CommentDao commentDao();
}

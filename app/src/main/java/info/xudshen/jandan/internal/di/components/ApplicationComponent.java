package info.xudshen.jandan.internal.di.components;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import info.xudshen.jandan.data.dao.AuthorDao;
import info.xudshen.jandan.data.dao.CategoryDao;
import info.xudshen.jandan.data.dao.CommentDao;
import info.xudshen.jandan.data.dao.DaoSession;
import info.xudshen.jandan.data.dao.DuoshuoCommentDao;
import info.xudshen.jandan.data.dao.FavoItemDao;
import info.xudshen.jandan.data.dao.JokeItemDao;
import info.xudshen.jandan.data.dao.MetaDao;
import info.xudshen.jandan.data.dao.ModelTrans;
import info.xudshen.jandan.data.dao.PicItemDao;
import info.xudshen.jandan.data.dao.PostDao;
import info.xudshen.jandan.data.dao.SimplePostDao;
import info.xudshen.jandan.data.dao.VideoItemDao;
import info.xudshen.jandan.domain.enums.CommentAction;
import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.repository.CommentRepository;
import info.xudshen.jandan.domain.repository.FavoRepository;
import info.xudshen.jandan.domain.repository.JokeRepository;
import info.xudshen.jandan.domain.repository.PicRepository;
import info.xudshen.jandan.domain.repository.PostRepository;
import info.xudshen.jandan.domain.repository.VideoRepository;
import info.xudshen.jandan.internal.di.modules.ApplicationModule;
import info.xudshen.jandan.internal.di.modules.DaoModule;
import info.xudshen.jandan.navigation.Navigator;
import info.xudshen.jandan.view.activity.BaseActivity;
import rx.subjects.PublishSubject;

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

    PublishSubject<CommentAction> commentActionSubject();

    DaoSession daoSession();

    ModelTrans modelTrans();

    PostRepository postRepository();

    PicRepository picRepository();

    JokeRepository jokeRepository();

    VideoRepository videoRepository();

    CommentRepository commentRepository();

    FavoRepository favoRepository();

    MetaDao metaDao();

    PostDao postDao();

    SimplePostDao simplePostDao();

    AuthorDao authorDao();

    CategoryDao categoryDao();

    CommentDao commentDao();

    PicItemDao picItemDao();

    JokeItemDao jokeItemDao();

    VideoItemDao videoItemDao();

    DuoshuoCommentDao duoshuoCommentDao();

    FavoItemDao favoItemDao();
}

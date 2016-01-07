package info.xudshen.jandan.internal.di.components;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import info.xudshen.jandan.data.dao.ArticleDao;
import info.xudshen.jandan.data.dao.DaoSession;
import info.xudshen.jandan.data.dao.JokeDao;
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

    DaoSession daoSession();

    ArticleDao articleDao();

    JokeDao jokeDao();
}

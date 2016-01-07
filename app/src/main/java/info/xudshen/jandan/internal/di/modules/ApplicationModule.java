package info.xudshen.jandan.internal.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.xudshen.jandan.JandanApp;
import info.xudshen.jandan.data.dao.DaoSession;
import info.xudshen.jandan.data.dao.JokeDao;
import info.xudshen.jandan.navigation.Navigator;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final JandanApp application;

    public ApplicationModule(JandanApp application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    Navigator provideNavigator() {
        return new Navigator();
    }

    @Provides
    @Singleton
    DaoSession provideDaoSession() {
        return this.application.daoSession;
    }
}

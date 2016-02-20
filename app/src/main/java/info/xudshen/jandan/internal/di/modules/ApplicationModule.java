package info.xudshen.jandan.internal.di.modules;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.xudshen.jandan.JandanApp;
import info.xudshen.jandan.UIThread;
import info.xudshen.jandan.data.dao.DaoMaster;
import info.xudshen.jandan.data.dao.DaoSession;
import info.xudshen.jandan.data.executor.JobExecutor;
import info.xudshen.jandan.data.repository.PicDataRepository;
import info.xudshen.jandan.data.repository.PostDataRepository;
import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.repository.PicRepository;
import info.xudshen.jandan.domain.repository.PostRepository;
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
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    DaoSession provideDaoSession() {
        SQLiteOpenHelper openHelper = new DaoMaster.DevOpenHelper(this.application, "default", null);
        SQLiteDatabase db = openHelper.getWritableDatabase();

        return (new DaoMaster(db)).newSession().setContext(this.application);
    }

    @Provides
    @Singleton
    PostRepository providePostRepository(PostDataRepository postDataRepository) {
        return postDataRepository;
    }

    @Provides
    @Singleton
    PicRepository providePicRepository(PicDataRepository picDataRepository) {
        return picDataRepository;
    }
}

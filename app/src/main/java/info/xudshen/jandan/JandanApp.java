package info.xudshen.jandan;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import info.xudshen.jandan.data.dao.ModelContentProvider;
import info.xudshen.jandan.internal.di.components.ApplicationComponent;
import info.xudshen.jandan.internal.di.components.DaggerApplicationComponent;
import info.xudshen.jandan.internal.di.modules.ApplicationModule;


/**
 * Created by xudshen on 15/9/24.
 */
public class JandanApp extends MultiDexApplication {
    private ApplicationComponent applicationComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        this.initializeInjector();
        ModelContentProvider.daoSession = getApplicationComponent().daoSession();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}

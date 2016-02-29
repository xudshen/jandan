package info.xudshen.jandan;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.xudshen.jandan.data.dao.ModelContentProvider;
import info.xudshen.jandan.internal.di.components.ApplicationComponent;
import info.xudshen.jandan.internal.di.components.DaggerApplicationComponent;
import info.xudshen.jandan.internal.di.modules.ApplicationModule;
import info.xudshen.jandan.view.activity.JandanSettingActivity;
import info.xudshen.jandan.view.adapter.AppAdapters;


/**
 * Created by xudshen on 15/9/24.
 */
public class JandanApp extends MultiDexApplication {
    private static final Logger logger = LoggerFactory.getLogger(JandanApp.class);
    private ApplicationComponent applicationComponent;

    public static RefWatcher getRefWatcher(Context context) {
        JandanApp application = (JandanApp) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);

        this.initializeInjector();
        ModelContentProvider.daoSession = getApplicationComponent().daoSession();

        preLoadImageQuality();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    private void preLoadImageQuality() {
        AppAdapters.IMAGE_QUALITY = JandanSettingActivity.getSettingImageQuality(this);
        logger.info("load IMAGE_QUALITY={}", AppAdapters.IMAGE_QUALITY);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}

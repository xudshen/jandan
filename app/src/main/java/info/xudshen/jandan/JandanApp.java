package info.xudshen.jandan;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

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

        MobclickAgent.setCheckDevice(false);

        this.initializeInjector();
        ModelContentProvider.daoSession = getApplicationComponent().daoSession();

        preLoadImageQuality();

        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP

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

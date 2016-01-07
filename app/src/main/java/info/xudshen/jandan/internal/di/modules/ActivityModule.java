package info.xudshen.jandan.internal.di.modules;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import info.xudshen.jandan.internal.di.PerActivity;

@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }
}

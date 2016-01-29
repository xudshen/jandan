package info.xudshen.jandan.internal.di.components;

import android.app.Activity;

import dagger.Component;
import info.xudshen.jandan.internal.di.PerActivity;
import info.xudshen.jandan.internal.di.modules.ActivityModule;
import info.xudshen.jandan.view.activity.MainActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);

    //Exposed to sub-graphs.
    Activity activity();
}

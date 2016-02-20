package info.xudshen.jandan.internal.di.components;

import dagger.Component;
import info.xudshen.jandan.internal.di.PerActivity;
import info.xudshen.jandan.internal.di.modules.ActivityModule;
import info.xudshen.jandan.internal.di.modules.PicModule;
import info.xudshen.jandan.view.fragment.PicListFragment;

/**
 * Created by xudshen on 16/2/20.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, PicModule.class})
public interface PicComponent {
    void inject(PicListFragment picListFragment);
}

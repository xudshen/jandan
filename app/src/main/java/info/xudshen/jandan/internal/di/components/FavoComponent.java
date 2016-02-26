package info.xudshen.jandan.internal.di.components;

import dagger.Component;
import info.xudshen.jandan.internal.di.PerActivity;
import info.xudshen.jandan.internal.di.modules.ActivityModule;
import info.xudshen.jandan.internal.di.modules.FavoModule;
import info.xudshen.jandan.view.fragment.FavoFragment;

/**
 * Created by xudshen on 16/2/26.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, FavoModule.class})
public interface FavoComponent {
    void inject(FavoFragment favoFragment);
}

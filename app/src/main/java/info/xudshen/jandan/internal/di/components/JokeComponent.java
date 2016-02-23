package info.xudshen.jandan.internal.di.components;

import dagger.Component;
import info.xudshen.jandan.internal.di.PerActivity;
import info.xudshen.jandan.internal.di.modules.ActivityModule;
import info.xudshen.jandan.internal.di.modules.JokeModule;
import info.xudshen.jandan.view.fragment.JokeListFragment;

/**
 * Created by xudshen on 16/2/20.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, JokeModule.class})
public interface JokeComponent {
//    void inject(JokeDetailFragment jokeDetailFragment);

    void inject(JokeListFragment jokeListFragment);

//    void inject(JokeReaderPagerAdapter jokeReaderPagerAdapter);
}

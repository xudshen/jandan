package info.xudshen.jandan.internal.di.components;

import dagger.Component;
import info.xudshen.jandan.internal.di.PerActivity;
import info.xudshen.jandan.view.fragment.PostListFragment;

/**
 * Created by xudshen on 16/1/7.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class)
public interface PostComponent {
    void inject(PostListFragment postListFragment);
}

package info.xudshen.jandan.internal.di.components;

import dagger.Component;
import info.xudshen.jandan.internal.di.PerActivity;
import info.xudshen.jandan.internal.di.modules.ActivityModule;
import info.xudshen.jandan.internal.di.modules.PostModule;
import info.xudshen.jandan.view.fragment.ReadLaterFragment;
import info.xudshen.jandan.view.fragment.PostHubFragment;
import info.xudshen.jandan.view.fragment.PostListFragment;

/**
 * Created by xudshen on 16/1/7.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, PostModule.class})
public interface PostComponent {
    void inject(PostHubFragment postHubFragment);

    void inject(PostListFragment postListFragment);

    void inject(ReadLaterFragment readLaterFragment);
}

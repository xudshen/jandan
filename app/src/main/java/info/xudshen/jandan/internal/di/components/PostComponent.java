package info.xudshen.jandan.internal.di.components;

import dagger.Component;
import info.xudshen.jandan.internal.di.PerActivity;
import info.xudshen.jandan.internal.di.modules.ActivityModule;
import info.xudshen.jandan.internal.di.modules.PostModule;
import info.xudshen.jandan.view.adapter.ItemReaderPagerAdapter;
import info.xudshen.jandan.view.fragment.HomeFragment;
import info.xudshen.jandan.view.fragment.PostDetailFragment;
import info.xudshen.jandan.view.fragment.PostListFragment;
import info.xudshen.jandan.view.fragment.ReadLaterFragment;

/**
 * Created by xudshen on 16/1/7.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, PostModule.class})
public interface PostComponent {
    void inject(HomeFragment homeFragment);

    void inject(PostListFragment postListFragment);

    void inject(PostDetailFragment postDetailFragment);

    void inject(ItemReaderPagerAdapter itemReaderPagerAdapter);

    void inject(ReadLaterFragment readLaterFragment);
}

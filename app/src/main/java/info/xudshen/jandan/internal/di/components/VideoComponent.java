package info.xudshen.jandan.internal.di.components;

import dagger.Component;
import info.xudshen.jandan.internal.di.PerActivity;
import info.xudshen.jandan.internal.di.modules.ActivityModule;
import info.xudshen.jandan.internal.di.modules.VideoModule;
//import info.xudshen.jandan.view.adapter.VideoReaderPagerAdapter;
//import info.xudshen.jandan.view.fragment.VideoDetailFragment;
import info.xudshen.jandan.view.fragment.VideoListFragment;

/**
 * Created by xudshen on 16/2/20.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, VideoModule.class})
public interface VideoComponent {
//    void inject(VideoDetailFragment videoDetailFragment);

    void inject(VideoListFragment videoListFragment);

//    void inject(VideoReaderPagerAdapter videoReaderPagerAdapter);
}

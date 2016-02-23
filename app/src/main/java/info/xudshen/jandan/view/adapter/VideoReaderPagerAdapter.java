package info.xudshen.jandan.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

import de.greenrobot.dao.query.LazyList;
import info.xudshen.jandan.data.dao.VideoItemDao;
import info.xudshen.jandan.domain.interactor.IterableUseCase;
import info.xudshen.jandan.domain.model.VideoItem;
import info.xudshen.jandan.view.LoadDataView;
import info.xudshen.jandan.view.fragment.VideoDetailFragment;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by xudshen on 16/2/21.
 * for video list
 */
public class VideoReaderPagerAdapter extends FragmentStatePagerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(VideoReaderPagerAdapter.class);
    private LoadDataView loadDataView;
    @Inject
    VideoItemDao videoItemDao;
    @Named("videoList")
    @Inject
    IterableUseCase getVideoListUseCase;

    private LazyList<VideoItem> videoItems;

    public VideoReaderPagerAdapter(FragmentManager fm, LoadDataView loadDataView) {
        super(fm);
        this.loadDataView = loadDataView;
    }

    public void initialize() {
        this.videoItems = this.videoItemDao.queryBuilder().orderDesc(VideoItemDao.Properties.Date).listLazyUncached();
    }

    @Override
    public Fragment getItem(int position) {
        if (this.videoItems.size() - 1 == position) {
            //fetch more
            this.getVideoListUseCase.executeNext(this.loadDataView.bindToLifecycle(), new Subscriber() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    VideoReaderPagerAdapter.this.loadDataView.showError("");
                }

                @Override
                public void onNext(Object o) {
                    VideoReaderPagerAdapter.this.videoItems =
                            VideoReaderPagerAdapter.this.videoItemDao.queryBuilder()
                                    .orderDesc(VideoItemDao.Properties.Date).listLazyUncached();
                    Observable.empty().observeOn(AndroidSchedulers.mainThread()).doOnCompleted(() -> {
                        logger.info("refresh");
                        VideoReaderPagerAdapter.this.notifyDataSetChanged();
                    }).subscribe();
                }
            });
        }
        return VideoDetailFragment.newInstance(this.videoItems.get(position).getVideoId());
    }

    @Override
    public int getCount() {
        return this.videoItems.size();
    }
}

package info.xudshen.jandan.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

import de.greenrobot.dao.query.LazyList;
import info.xudshen.jandan.data.dao.SimplePostDao;
import info.xudshen.jandan.domain.interactor.IterableUseCase;
import info.xudshen.jandan.domain.model.SimplePost;
import info.xudshen.jandan.view.LoadDataView;
import info.xudshen.jandan.view.fragment.PostDetailFragment;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by xudshen on 16/1/28.
 */
public class ItemReaderPagerAdapter extends FragmentStatePagerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ItemReaderPagerAdapter.class);
    private LoadDataView loadDataView;
    @Inject
    SimplePostDao simplePostDao;
    @Named("postList")
    @Inject
    IterableUseCase getPostListUseCase;

    private LazyList<SimplePost> simplePosts;

    public ItemReaderPagerAdapter(FragmentManager fm, LoadDataView loadDataView) {
        super(fm);
        this.loadDataView = loadDataView;
    }

    public void initialize() {
        this.simplePosts = this.simplePostDao.queryBuilder().orderDesc(SimplePostDao.Properties.Date).listLazyUncached();
    }

    @Override
    public Fragment getItem(int position) {
        if (this.simplePosts.size() - 1 == position) {
            //fetch more
            this.getPostListUseCase.executeNext(this.loadDataView.bindToLifecycle(), new Subscriber() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    ItemReaderPagerAdapter.this.loadDataView.showError("");
                }

                @Override
                public void onNext(Object o) {
                    ItemReaderPagerAdapter.this.simplePosts =
                            ItemReaderPagerAdapter.this.simplePostDao.queryBuilder()
                                    .orderDesc(SimplePostDao.Properties.Date).listLazyUncached();
                    Observable.empty().observeOn(AndroidSchedulers.mainThread()).doOnCompleted(() -> {
                        logger.info("refresh");
                        ItemReaderPagerAdapter.this.notifyDataSetChanged();
                    }).subscribe();
                }
            });
        }
        return PostDetailFragment.newInstance(this.simplePosts.get(position).getPostId());
    }

    @Override
    public int getCount() {
        return this.simplePosts.size();
    }
}

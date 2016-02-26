package info.xudshen.jandan.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

import de.greenrobot.dao.query.LazyList;
import info.xudshen.jandan.data.dao.FavoItemDao;
import info.xudshen.jandan.data.dao.SimplePostDao;
import info.xudshen.jandan.domain.enums.ReaderItemType;
import info.xudshen.jandan.domain.interactor.IterableUseCase;
import info.xudshen.jandan.domain.model.SimplePost;
import info.xudshen.jandan.view.LoadDataView;
import info.xudshen.jandan.view.fragment.PostDetailFragment;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by xudshen on 16/1/28.
 * for post list
 */
public class PostReaderPagerAdapter extends FragmentStatePagerAdapter implements IItemInfo {
    private static final Logger logger = LoggerFactory.getLogger(PostReaderPagerAdapter.class);
    private LoadDataView loadDataView;
    @Inject
    SimplePostDao simplePostDao;
    @Inject
    FavoItemDao favoItemDao;
    @Named("postList")
    @Inject
    IterableUseCase getPostListUseCase;

    private LazyList<SimplePost> simplePosts;

    public PostReaderPagerAdapter(FragmentManager fm, LoadDataView loadDataView) {
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
                    PostReaderPagerAdapter.this.loadDataView.showError("");
                }

                @Override
                public void onNext(Object o) {
                    PostReaderPagerAdapter.this.simplePosts =
                            PostReaderPagerAdapter.this.simplePostDao.queryBuilder()
                                    .orderDesc(SimplePostDao.Properties.Date).listLazyUncached();
                    Observable.empty().observeOn(AndroidSchedulers.mainThread()).doOnCompleted(() -> {
                        logger.info("refresh");
                        PostReaderPagerAdapter.this.notifyDataSetChanged();
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

    @Override
    public <T> T getAdapterItem(int position) {
        return (T) this.simplePosts.get(position);
    }

    @Override
    public String getAdapterItemId(int position) {
        SimplePost post = this.simplePosts.get(position);
        return post != null ? post.getPostId() + "" : null;
    }

    @Override
    public ReaderItemType getAdapterItemType(int position) {
        return ReaderItemType.SimplePost;
    }

    @Override
    public Boolean isInFavoItem(int position) {
        return this.favoItemDao.queryBuilder().where(
                FavoItemDao.Properties.ActualId.eq(getAdapterItemId(position)),
                FavoItemDao.Properties.Type.eq(getAdapterItemType(position))
        ).buildCount().forCurrentThread().count() > 0;
    }
}

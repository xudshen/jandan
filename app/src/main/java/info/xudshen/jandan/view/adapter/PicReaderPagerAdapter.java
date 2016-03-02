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
import info.xudshen.jandan.data.dao.PicItemDao;
import info.xudshen.jandan.domain.enums.ReaderItemType;
import info.xudshen.jandan.domain.interactor.IterableUseCase;
import info.xudshen.jandan.domain.model.PicItem;
import info.xudshen.jandan.view.LoadDataView;
import info.xudshen.jandan.view.fragment.PicDetailFragment;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by xudshen on 16/2/21.
 * for pic list
 */
public class PicReaderPagerAdapter extends FragmentStatePagerAdapter implements IItemInfo {
    private static final Logger logger = LoggerFactory.getLogger(PicReaderPagerAdapter.class);
    private LoadDataView loadDataView;
    @Inject
    PicItemDao picItemDao;
    @Inject
    FavoItemDao favoItemDao;
    @Named("picList")
    @Inject
    IterableUseCase getPicListUseCase;

    private int mCount;
    private LazyList<PicItem> picItems;

    public PicReaderPagerAdapter(FragmentManager fm, LoadDataView loadDataView) {
        super(fm);
        this.loadDataView = loadDataView;
    }

    public void initialize() {
        this.picItems = this.picItemDao.queryBuilder().orderDesc(PicItemDao.Properties.Date).listLazyUncached();
        this.mCount = this.picItems.size();
    }

    @Override
    public Fragment getItem(int position) {
        if (this.picItems.size() - 1 == position) {
            //fetch more
            this.getPicListUseCase.executeNext(this.loadDataView.bindToLifecycle(), new Subscriber() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    PicReaderPagerAdapter.this.loadDataView.showError("");
                }

                @Override
                public void onNext(Object o) {
                    PicReaderPagerAdapter.this.picItems =
                            PicReaderPagerAdapter.this.picItemDao.queryBuilder()
                                    .orderDesc(PicItemDao.Properties.Date).listLazyUncached();
                    Observable.empty().observeOn(AndroidSchedulers.mainThread()).doOnCompleted(() -> {
                        mCount = picItems.size();
                        PicReaderPagerAdapter.this.notifyDataSetChanged();
                    }).subscribe();
                }
            });
        }
        return PicDetailFragment.newInstance(this.picItems.get(position).getPicId());
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public <T> T getAdapterItem(int position) {
        return (T) this.picItems.get(position);
    }

    @Override
    public String getAdapterItemId(int position) {
        PicItem picItem = this.picItems.get(position);
        return picItem != null ? picItem.getPicId() + "" : null;
    }

    @Override
    public ReaderItemType getAdapterItemType(int position) {
        return ReaderItemType.SimplePic;
    }

    @Override
    public Boolean isInFavoItem(int position) {
        return this.favoItemDao.queryBuilder().where(
                FavoItemDao.Properties.ActualId.eq(getAdapterItemId(position)),
                FavoItemDao.Properties.Type.eq(getAdapterItemType(position))
        ).buildCount().forCurrentThread().count() > 0;
    }
}

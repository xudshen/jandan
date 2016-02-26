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
import info.xudshen.jandan.domain.model.FavoItem;
import info.xudshen.jandan.domain.model.FavoItemTrans;
import info.xudshen.jandan.domain.model.SimplePost;
import info.xudshen.jandan.view.LoadDataView;
import info.xudshen.jandan.view.fragment.BlankFragment;
import info.xudshen.jandan.view.fragment.JokeDetailFragment;
import info.xudshen.jandan.view.fragment.PicDetailFragment;
import info.xudshen.jandan.view.fragment.PostDetailFragment;
import info.xudshen.jandan.view.fragment.VideoDetailFragment;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by xudshen on 16/1/28.
 * for post list
 */
public class FavoReaderPagerAdapter extends FragmentStatePagerAdapter implements IItemInfo {
    private static final Logger logger = LoggerFactory.getLogger(FavoReaderPagerAdapter.class);
    @Inject
    FavoItemDao favoItemDao;

    private LazyList<FavoItem> favoItems;

    public FavoReaderPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void initialize() {
        this.favoItems = this.favoItemDao.queryBuilder().orderDesc(FavoItemDao.Properties.AddDate).listLazyUncached();
    }

    @Override
    public Fragment getItem(int position) {
        FavoItem favoItem = this.favoItems.get(position);
        switch (favoItem.getType()) {
            case SimplePost: {
                return PostDetailFragment.newInstance(FavoItemTrans.toSimplePost(favoItem).getPostId());
            }
            case SimpleJoke: {
                return JokeDetailFragment.newInstance(FavoItemTrans.toJokeItem(favoItem).getJokeId(), favoItem);
            }
            case SimplePic: {
                return PicDetailFragment.newInstance(FavoItemTrans.toJokeItem(favoItem).getJokeId(), favoItem);
            }
            case SimpleVideo: {
                return VideoDetailFragment.newInstance(FavoItemTrans.toJokeItem(favoItem).getJokeId(), favoItem);
            }
        }
        return BlankFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return this.favoItems.size();
    }

    @Override
    public <T> T getAdapterItem(int position) {
        FavoItem favoItem = this.favoItems.get(position);
        return FavoItemTrans.to(favoItem);
    }

    @Override
    public String getAdapterItemId(int position) {
        FavoItem favoItem = this.favoItems.get(position);
        return favoItem.getActualId();
    }

    @Override
    public ReaderItemType getAdapterItemType(int position) {
        return this.favoItems.get(position).getType();
    }

    @Override
    public Boolean isInFavoItem(int position) {
        //always query from db, since in Reader mode, the item will exist until user exit
        return this.favoItemDao.queryBuilder().where(
                FavoItemDao.Properties.ActualId.eq(getAdapterItemId(position)),
                FavoItemDao.Properties.Type.eq(getAdapterItemType(position))
        ).buildCount().forCurrentThread().count() > 0;
    }
}

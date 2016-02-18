package info.xudshen.jandan.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.greenrobot.dao.query.LazyList;
import info.xudshen.jandan.data.dao.SimplePostDao;
import info.xudshen.jandan.domain.enums.ReaderItemType;
import info.xudshen.jandan.domain.model.SimplePost;
import info.xudshen.jandan.view.fragment.BlankFragment;
import info.xudshen.jandan.view.fragment.PostDetailFragment;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xudshen on 16/1/28.
 */
public class ItemReaderPagerAdapter extends FragmentStatePagerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ItemReaderPagerAdapter.class);
    private ReaderItemType type;
    private SimplePostDao simplePostDao;
    private LazyList<SimplePost> simplePosts;

    public ItemReaderPagerAdapter(FragmentManager fm, ReaderItemType type, SimplePostDao simplePostDao) {
        super(fm);
        this.type = type;
        this.simplePostDao = simplePostDao;
        this.simplePosts = this.simplePostDao.queryBuilder().orderDesc(SimplePostDao.Properties.Date).listLazyUncached();
    }

    @Override
    public Fragment getItem(int position) {
        if (this.simplePosts.size() - 1 == position) {
            //fetch more
            
            this.simplePosts = this.simplePostDao.queryBuilder().orderDesc(SimplePostDao.Properties.Date).listLazyUncached();
            Observable.empty().observeOn(AndroidSchedulers.mainThread()).doOnCompleted(() -> {
                logger.info("refresh");
                ItemReaderPagerAdapter.this.notifyDataSetChanged();
            }).subscribe();
        }
        return PostDetailFragment.newInstance(this.simplePosts.get(position).getPostId());
    }

    @Override
    public int getCount() {
        return this.simplePosts.size();
    }
}

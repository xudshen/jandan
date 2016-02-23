package info.xudshen.jandan.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

import de.greenrobot.dao.query.LazyList;
import info.xudshen.jandan.data.dao.JokeItemDao;
import info.xudshen.jandan.domain.interactor.IterableUseCase;
import info.xudshen.jandan.domain.model.JokeItem;
import info.xudshen.jandan.view.LoadDataView;
import info.xudshen.jandan.view.fragment.JokeDetailFragment;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by xudshen on 16/2/21.
 * for joke list
 */
public class JokeReaderPagerAdapter extends FragmentStatePagerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(JokeReaderPagerAdapter.class);
    private LoadDataView loadDataView;
    @Inject
    JokeItemDao jokeItemDao;
    @Named("jokeList")
    @Inject
    IterableUseCase getJokeListUseCase;

    private LazyList<JokeItem> jokeItems;

    public JokeReaderPagerAdapter(FragmentManager fm, LoadDataView loadDataView) {
        super(fm);
        this.loadDataView = loadDataView;
    }

    public void initialize() {
        this.jokeItems = this.jokeItemDao.queryBuilder().orderDesc(JokeItemDao.Properties.Date).listLazyUncached();
    }

    @Override
    public Fragment getItem(int position) {
        if (this.jokeItems.size() - 1 == position) {
            //fetch more
            this.getJokeListUseCase.executeNext(this.loadDataView.bindToLifecycle(), new Subscriber() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    JokeReaderPagerAdapter.this.loadDataView.showError("");
                }

                @Override
                public void onNext(Object o) {
                    JokeReaderPagerAdapter.this.jokeItems =
                            JokeReaderPagerAdapter.this.jokeItemDao.queryBuilder()
                                    .orderDesc(JokeItemDao.Properties.Date).listLazyUncached();
                    Observable.empty().observeOn(AndroidSchedulers.mainThread()).doOnCompleted(() -> {
                        logger.info("refresh");
                        JokeReaderPagerAdapter.this.notifyDataSetChanged();
                    }).subscribe();
                }
            });
        }
        return JokeDetailFragment.newInstance(this.jokeItems.get(position).getJokeId());
    }

    @Override
    public int getCount() {
        return this.jokeItems.size();
    }
}
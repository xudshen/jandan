package info.xudshen.jandan.data.repository.datasource.impl;

import org.apache.commons.lang3.NotImplementedException;

import java.util.List;

import info.xudshen.jandan.data.dao.PicItemDao;
import info.xudshen.jandan.data.exception.PicNotFoundException;
import info.xudshen.jandan.data.repository.datasource.PicDataStore;
import info.xudshen.jandan.domain.model.PicItem;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by xudshen on 16/2/16.
 */
public class LocalPicDataStore implements PicDataStore {
    private final PicItemDao picItemDao;

    public LocalPicDataStore(PicItemDao picItemDao) {
        this.picItemDao = picItemDao;
    }

    @Override
    public Observable<PicItem> pic(Long picId) {
        return Observable.create(new Observable.OnSubscribe<PicItem>() {
            @Override
            public void call(Subscriber<? super PicItem> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    PicItem picItem = LocalPicDataStore.this.picItemDao.queryBuilder()
                            .where(PicItemDao.Properties.PicId.eq(picId))
                            .build().forCurrentThread().unique();
                    if (picItem == null) {
                        subscriber.onError(new PicNotFoundException());
                    } else {
                        subscriber.onNext(picItem);
                        subscriber.onCompleted();
                    }
                }
            }
        });
    }

    @Override
    public Observable<List<PicItem>> picList() {
        throw new NotImplementedException("");
    }

    @Override
    public Observable<List<PicItem>> picListNext() {
        throw new NotImplementedException("");
    }
}
package info.xudshen.jandan.data.repository.datasource.impl;

import org.apache.commons.lang3.NotImplementedException;

import java.util.List;

import info.xudshen.jandan.data.dao.FavoItemDao;
import info.xudshen.jandan.data.exception.FavoItemNotFoundException;
import info.xudshen.jandan.data.repository.datasource.FavoDataStore;
import info.xudshen.jandan.domain.enums.ReaderItemType;
import info.xudshen.jandan.domain.model.FavoItem;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by xudshen on 16/2/25.
 */
public class LocalFavoDataStore implements FavoDataStore {
    private final FavoItemDao favoItemDao;

    public LocalFavoDataStore(FavoItemDao favoItemDao) {
        this.favoItemDao = favoItemDao;
    }

    @Override
    public Observable<FavoItem> favoItem(ReaderItemType type, Long actualId) {
        return Observable.create(new Observable.OnSubscribe<FavoItem>() {
            @Override
            public void call(Subscriber<? super FavoItem> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    FavoItem favoItem = LocalFavoDataStore.this.favoItemDao.queryBuilder()
                            .where(FavoItemDao.Properties.Type.eq(type),
                                    FavoItemDao.Properties.ActualId.eq(actualId))
                            .build().forCurrentThread().unique();
                    if (favoItem == null) {
                        subscriber.onError(new FavoItemNotFoundException());
                    } else {
                        subscriber.onNext(favoItem);
                        subscriber.onCompleted();
                    }
                }
            }
        });
    }

    @Override
    public Observable<List<FavoItem>> favoItemList() {
        throw new NotImplementedException("");
    }

    @Override
    public Observable<Boolean> saveFavoItem(FavoItem favoItem) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    try {
                        favoItemDao.insertOrReplace(favoItem);
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                }
            }
        });
    }

    @Override
    public Observable<Boolean> deleteFavoItem(ReaderItemType type, String actualId) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    if (LocalFavoDataStore.this.favoItemDao.queryBuilder()
                            .where(FavoItemDao.Properties.Type.eq(type),
                                    FavoItemDao.Properties.ActualId.eq(actualId))
                            .buildCount().forCurrentThread().count() > 0) {
                        try {
                            FavoItem favoItem = LocalFavoDataStore.this.favoItemDao.queryBuilder()
                                    .where(FavoItemDao.Properties.Type.eq(type),
                                            FavoItemDao.Properties.ActualId.eq(actualId))
                                    .build().forCurrentThread().unique();
                            LocalFavoDataStore.this.favoItemDao.delete(favoItem);
                            subscriber.onNext(true);
                            subscriber.onCompleted();
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                    } else {
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                    }
                }
            }
        });
    }
}

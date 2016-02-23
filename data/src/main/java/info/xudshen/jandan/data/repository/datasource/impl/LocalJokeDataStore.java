package info.xudshen.jandan.data.repository.datasource.impl;

import org.apache.commons.lang3.NotImplementedException;

import java.util.List;

import info.xudshen.jandan.data.dao.JokeItemDao;
import info.xudshen.jandan.data.exception.JokeNotFoundException;
import info.xudshen.jandan.data.repository.datasource.JokeDataStore;
import info.xudshen.jandan.domain.model.JokeItem;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by xudshen on 16/2/16.
 */
public class LocalJokeDataStore implements JokeDataStore {
    private final JokeItemDao jokeItemDao;

    public LocalJokeDataStore(JokeItemDao jokeItemDao) {
        this.jokeItemDao = jokeItemDao;
    }

    @Override
    public Observable<JokeItem> joke(Long jokeId) {
        return Observable.create(new Observable.OnSubscribe<JokeItem>() {
            @Override
            public void call(Subscriber<? super JokeItem> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    JokeItem jokeItem = LocalJokeDataStore.this.jokeItemDao.queryBuilder()
                            .where(JokeItemDao.Properties.JokeId.eq(jokeId))
                            .build().forCurrentThread().unique();
                    if (jokeItem == null) {
                        subscriber.onError(new JokeNotFoundException());
                    } else {
                        subscriber.onNext(jokeItem);
                        subscriber.onCompleted();
                    }
                }
            }
        });
    }

    @Override
    public Observable<List<JokeItem>> jokeList() {
        throw new NotImplementedException("");
    }

    @Override
    public Observable<List<JokeItem>> jokeListNext() {
        throw new NotImplementedException("");
    }
}
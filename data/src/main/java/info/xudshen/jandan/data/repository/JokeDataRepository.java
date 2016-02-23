package info.xudshen.jandan.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import info.xudshen.jandan.data.repository.datasource.impl.JokeDataStoreFactory;
import info.xudshen.jandan.domain.model.JokeItem;
import info.xudshen.jandan.domain.repository.JokeRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/20.
 */
@Singleton
public class JokeDataRepository implements JokeRepository {
    private final JokeDataStoreFactory jokeDataStoreFactory;

    @Inject
    public JokeDataRepository(JokeDataStoreFactory jokeDataStoreFactory) {
        this.jokeDataStoreFactory = jokeDataStoreFactory;
    }

    @Override
    public Observable<JokeItem> joke(Long jokeId) {
        return this.jokeDataStoreFactory.createLocalCloudDataStore().joke(jokeId);
    }

    @Override
    public Observable<List<JokeItem>> jokeList() {
        return this.jokeDataStoreFactory.createCloudDataStore().jokeList();
    }

    @Override
    public Observable<List<JokeItem>> jokeListNextPage() {
        return this.jokeDataStoreFactory.createCloudDataStore().jokeListNext();
    }
}

package info.xudshen.jandan.data.repository.datasource.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import info.xudshen.jandan.data.api.ApiAdapter;
import info.xudshen.jandan.data.dao.MetaDao;
import info.xudshen.jandan.data.dao.JokeItemDao;
import info.xudshen.jandan.data.repository.datasource.JokeDataStore;

/**
 * Created by xudshen on 16/2/20.
 */
@Singleton
public class JokeDataStoreFactory {
    private final JokeItemDao jokeItemDao;
    private final MetaDao metaDao;

    @Inject
    public JokeDataStoreFactory(JokeItemDao jokeItemDao, MetaDao metaDao) {
        if (jokeItemDao == null || metaDao == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.jokeItemDao = jokeItemDao;
        this.metaDao = metaDao;
    }

    public JokeDataStore createLocalCloudDataStore() {
        JokeDataStore jokeDataStore = new LocalJokeDataStore(jokeItemDao);
        return jokeDataStore;
    }


    public JokeDataStore createCloudDataStore() {
        return new CloudJokeDataStore(ApiAdapter.getCommonItemService(), ApiAdapter.getCommentService(),
                jokeItemDao, metaDao);
    }
}

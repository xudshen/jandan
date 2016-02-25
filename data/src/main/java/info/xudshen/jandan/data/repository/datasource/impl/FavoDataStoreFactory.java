package info.xudshen.jandan.data.repository.datasource.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import info.xudshen.jandan.data.dao.FavoItemDao;
import info.xudshen.jandan.data.repository.datasource.FavoDataStore;

/**
 * Created by xudshen on 16/2/25.
 */
@Singleton
public class FavoDataStoreFactory {
    private final FavoItemDao favoItemDao;

    @Inject
    public FavoDataStoreFactory(FavoItemDao favoItemDao) {
        if (favoItemDao == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.favoItemDao = favoItemDao;
    }

    public FavoDataStore createLocalCloudDataStore() {
        FavoDataStore favoDataStore = new LocalFavoDataStore(favoItemDao);
        return favoDataStore;
    }
}

package info.xudshen.jandan.data.repository.datasource.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import info.xudshen.jandan.data.api.ApiAdapter;
import info.xudshen.jandan.data.dao.MetaDao;
import info.xudshen.jandan.data.dao.PicItemDao;
import info.xudshen.jandan.data.repository.datasource.PicDataStore;

/**
 * Created by xudshen on 16/2/20.
 */
@Singleton
public class PicDataStoreFactory {
    private final PicItemDao picItemDao;
    private final MetaDao metaDao;

    @Inject
    public PicDataStoreFactory(PicItemDao picItemDao, MetaDao metaDao) {
        if (picItemDao == null || metaDao == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.picItemDao = picItemDao;
        this.metaDao = metaDao;
    }

    public PicDataStore createLocalCloudDataStore() {
        PicDataStore picDataStore = new LocalPicDataStore(picItemDao);
        return picDataStore;
    }


    public PicDataStore createCloudDataStore() {
        return new CloudPicDataStore(ApiAdapter.getPicService(),
                picItemDao, metaDao);
    }
}

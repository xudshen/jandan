package info.xudshen.jandan.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import info.xudshen.jandan.data.repository.datasource.impl.FavoDataStoreFactory;
import info.xudshen.jandan.domain.enums.ReaderItemType;
import info.xudshen.jandan.domain.model.FavoItem;
import info.xudshen.jandan.domain.repository.FavoRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/25.
 */
@Singleton
public class FavoDataRepository implements FavoRepository {
    private final FavoDataStoreFactory favoDataStoreFactory;

    @Inject
    public FavoDataRepository(FavoDataStoreFactory favoDataStoreFactory) {
        this.favoDataStoreFactory = favoDataStoreFactory;
    }

    @Override
    public Observable<FavoItem> favoItem(ReaderItemType type, Long actualId) {
        return this.favoDataStoreFactory.createLocalCloudDataStore().favoItem(type, actualId);
    }

    @Override
    public Observable<List<FavoItem>> favoItemList() {
        return this.favoDataStoreFactory.createLocalCloudDataStore().favoItemList();
    }


    @Override
    public Observable<Boolean> saveFavoItem(FavoItem favoItem) {
        return this.favoDataStoreFactory.createLocalCloudDataStore().saveFavoItem(favoItem);
    }
}

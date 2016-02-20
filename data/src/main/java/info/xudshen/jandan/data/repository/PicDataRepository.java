package info.xudshen.jandan.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import info.xudshen.jandan.data.repository.datasource.impl.PicDataStoreFactory;
import info.xudshen.jandan.domain.repository.PicRepository;
import info.xudshen.jandan.domain.model.PicItem;
import rx.Observable;

/**
 * Created by xudshen on 16/2/20.
 */
@Singleton
public class PicDataRepository implements PicRepository {
    private final PicDataStoreFactory picDataStoreFactory;

    @Inject
    public PicDataRepository(PicDataStoreFactory picDataStoreFactory) {
        this.picDataStoreFactory = picDataStoreFactory;
    }

    @Override
    public Observable<List<PicItem>> picList() {
        return null;
    }

    @Override
    public Observable<List<PicItem>> picListNextPage() {
        return null;
    }
}

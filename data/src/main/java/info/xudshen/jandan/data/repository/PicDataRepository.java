package info.xudshen.jandan.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import info.xudshen.jandan.data.repository.datasource.impl.PicDataStoreFactory;
import info.xudshen.jandan.domain.enums.VoteResult;
import info.xudshen.jandan.domain.enums.VoteType;
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
    public Observable<PicItem> pic(Long picId) {
        return this.picDataStoreFactory.createLocalCloudDataStore().pic(picId);
    }

    @Override
    public Observable<List<PicItem>> picList() {
        return this.picDataStoreFactory.createCloudDataStore().picList();
    }

    @Override
    public Observable<List<PicItem>> picListNextPage() {
        return this.picDataStoreFactory.createCloudDataStore().picListNext();
    }

    @Override
    public Observable<VoteResult> voteCommonItem(Long commentId, VoteType voteType) {
        return this.picDataStoreFactory.createCloudDataStore().voteCommonItem(commentId, voteType);
    }
}

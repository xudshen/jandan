package info.xudshen.jandan.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import info.xudshen.jandan.data.repository.datasource.impl.CommentDataStoreFactory;
import info.xudshen.jandan.domain.model.DuoshuoComment;
import info.xudshen.jandan.domain.repository.CommentRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/22.
 */
@Singleton
public class CommentDataRepository implements CommentRepository {
    private final CommentDataStoreFactory commentDataStoreFactory;

    @Inject
    public CommentDataRepository(CommentDataStoreFactory commentDataStoreFactory) {
        this.commentDataStoreFactory = commentDataStoreFactory;
    }

    @Override
    public Observable<List<DuoshuoComment>> commentList(String threadKey) {
        return this.commentDataStoreFactory.createCloudDataStore().commentList(threadKey);
    }

    @Override
    public Observable<List<DuoshuoComment>> commentListNext(String threadKey) {
        return this.commentDataStoreFactory.createCloudDataStore().commentListNext(threadKey);
    }
}

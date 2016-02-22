package info.xudshen.jandan.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import info.xudshen.jandan.data.repository.datasource.impl.CommentDataStoreFactory;
import info.xudshen.jandan.domain.model.PicComment;
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
    public Observable<List<PicComment>> picCommentList(String threadKey) {
        return this.commentDataStoreFactory.createCloudDataStore().picCommentList(threadKey);
    }

    @Override
    public Observable<List<PicComment>> picCommentListNext(String threadKey) {
        return this.commentDataStoreFactory.createCloudDataStore().picCommentListNext(threadKey);
    }
}

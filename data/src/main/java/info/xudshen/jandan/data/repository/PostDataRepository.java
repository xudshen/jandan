package info.xudshen.jandan.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import info.xudshen.jandan.data.repository.datasource.PostDataStore;
import info.xudshen.jandan.data.repository.datasource.impl.PostDataStoreFactory;
import info.xudshen.jandan.domain.model.Comment;
import info.xudshen.jandan.domain.model.Post;
import info.xudshen.jandan.domain.model.SimplePost;
import info.xudshen.jandan.domain.repository.PostRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/16.
 */
@Singleton
public class PostDataRepository implements PostRepository {
    private final PostDataStoreFactory postDataStoreFactory;

    @Inject
    public PostDataRepository(PostDataStoreFactory postDataStoreFactory) {
        this.postDataStoreFactory = postDataStoreFactory;
    }

    @Override
    public Observable<Post> post(Long postId) {
        final PostDataStore postDataStore = this.postDataStoreFactory.create(postId);
        return postDataStore.post(postId);
    }

    @Override
    public Observable<List<SimplePost>> postList() {
        return this.postDataStoreFactory.createCloudDataStore().postList();
    }

    @Override
    public Observable<List<SimplePost>> postListNextPage() {
        return this.postDataStoreFactory.createCloudDataStore().postListNext();
    }

    @Override
    public Observable<List<Comment>> postCommentList(Long postId) {
        return this.postDataStoreFactory.createCloudDataStore().postCommentList(postId);
    }

    @Override
    public Observable<List<Comment>> postCommentListNext(Long postId) {
        return this.postDataStoreFactory.createCloudDataStore().postCommentListNext(postId);
    }

    @Override
    public Observable<Boolean> doPostComment(Long postId, String name, String email, String content) {
        return this.postDataStoreFactory.createCloudDataStore().doPostComment(
                postId, name, email, content);
    }
}

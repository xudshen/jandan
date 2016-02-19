package info.xudshen.jandan.data.repository.datasource;

import java.util.List;

import info.xudshen.jandan.domain.model.Comment;
import info.xudshen.jandan.domain.model.SimplePost;
import rx.Observable;

import info.xudshen.jandan.domain.model.Post;

/**
 * Created by xudshen on 16/2/16.
 */
public interface PostDataStore {
    Observable<Post> post(Long postId);

    Observable<List<SimplePost>> postList();

    Observable<List<SimplePost>> postListNext();

    Observable<List<Comment>> postCommentList(Long postId);
}

package info.xudshen.jandan.domain.repository;

import java.util.List;

import info.xudshen.jandan.domain.model.Comment;
import info.xudshen.jandan.domain.model.SimplePost;
import rx.Observable;

import info.xudshen.jandan.domain.model.Post;

/**
 * Created by xudshen on 16/1/8.
 */
public interface PostRepository {
    Observable<Post> post(Long postId);

    Observable<List<SimplePost>> postList();

    Observable<List<SimplePost>> postListNextPage();

    Observable<List<Comment>> postCommentList(Long postId);

    Observable<List<Comment>> postCommentListNext(Long postId);

    Observable<Boolean> doPostComment(Long postId, String name, String email, String content);
}
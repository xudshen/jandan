package info.xudshen.jandan.data.repository.datasource;

import java.util.List;

import info.xudshen.jandan.domain.enums.VoteResult;
import info.xudshen.jandan.domain.enums.VoteType;
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

    Observable<List<Comment>> postCommentListNext(Long postId);

    Observable<Boolean> doPostComment(Long postId, String name, String email, String content);

    Observable<VoteResult> voteComment(Long commentId, VoteType voteType);
}

package info.xudshen.jandan.domain.repository;

import java.util.List;

import rx.Observable;

import info.xudshen.jandan.domain.model.Post;

/**
 * Created by xudshen on 16/1/8.
 */
public interface PostRepository {
    Observable<Post> post(Long postId);

    Observable<List<Post>> postList(Long page);
}

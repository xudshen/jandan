package info.xudshen.jandan.data.repository.datasource;

import rx.Observable;

import info.xudshen.jandan.domain.model.Post;

/**
 * Created by xudshen on 16/2/16.
 */
public interface PostDataStore {
    Observable<Post> post(Long postId);
}

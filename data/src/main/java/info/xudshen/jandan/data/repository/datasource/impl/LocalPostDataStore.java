package info.xudshen.jandan.data.repository.datasource.impl;

import info.xudshen.jandan.data.dao.PostDao;
import info.xudshen.jandan.data.exception.PostNotFoundException;
import info.xudshen.jandan.data.repository.datasource.PostDataStore;
import info.xudshen.jandan.domain.model.Post;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by xudshen on 16/2/16.
 */
public class LocalPostDataStore implements PostDataStore {
    private final PostDao postDao;

    public LocalPostDataStore(PostDao postDao) {
        this.postDao = postDao;
    }

    @Override
    public Observable<Post> post(Long postId) {
        return Observable.create(new Observable.OnSubscribe<Post>() {
            @Override
            public void call(Subscriber<? super Post> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    Post post = LocalPostDataStore.this.postDao.queryBuilder()
                            .where(PostDao.Properties.PostId.eq(postId))
                            .build().forCurrentThread().unique();
                    if (post == null) {
                        subscriber.onError(new PostNotFoundException());
                    } else {
                        subscriber.onNext(post);
                        subscriber.onCompleted();
                    }
                }
            }
        });
    }
}
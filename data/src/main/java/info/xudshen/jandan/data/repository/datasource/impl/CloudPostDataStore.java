package info.xudshen.jandan.data.repository.datasource.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.xudshen.jandan.data.api.IPostService;
import info.xudshen.jandan.data.api.response.PostResponse;
import info.xudshen.jandan.data.dao.PostDao;
import info.xudshen.jandan.data.repository.datasource.PostDataStore;
import info.xudshen.jandan.domain.model.Post;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by xudshen on 16/2/16.
 */
public class CloudPostDataStore implements PostDataStore {
    private static final Logger logger = LoggerFactory.getLogger(CloudPostDataStore.class);

    private final IPostService postService;
    private final PostDao postDao;

    public CloudPostDataStore(IPostService postService, PostDao postDao) {
        this.postService = postService;
        this.postDao = postDao;
    }

    @Override
    public Observable<Post> post(Long postId) {
        return this.postService.getPostAsync(postId, "").map(PostResponse::getPost)
                .doOnNext(post -> {
                    if (post != null) {
                        CloudPostDataStore.this.postDao.insert(post);
                    }
                });
    }
}

package info.xudshen.jandan.data.repository.datasource.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import info.xudshen.jandan.data.api.ApiAdapter;
import info.xudshen.jandan.data.dao.PostDao;
import info.xudshen.jandan.data.repository.datasource.PostDataStore;

/**
 * Created by xudshen on 16/2/16.
 */
@Singleton
public class PostDataStoreFactory {
    private final PostDao postDao;

    @Inject
    public PostDataStoreFactory(PostDao postDao) {
        if (postDao == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.postDao = postDao;
    }

    public PostDataStore create(Long postId) {
        PostDataStore postDataStore;

        if (this.postDao.queryBuilder().where(PostDao.Properties.PostId.eq(postId))
                .buildCount().forCurrentThread().count() > 0) {
            postDataStore = new LocalPostDataStore(postDao);
        } else {
            postDataStore = new CloudPostDataStore(ApiAdapter.getPostService(), postDao);
        }

        return postDataStore;
    }
}

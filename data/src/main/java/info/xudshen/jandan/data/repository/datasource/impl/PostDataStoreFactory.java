package info.xudshen.jandan.data.repository.datasource.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import info.xudshen.jandan.data.api.ApiAdapter;
import info.xudshen.jandan.data.dao.CommentDao;
import info.xudshen.jandan.data.dao.MetaDao;
import info.xudshen.jandan.data.dao.PostDao;
import info.xudshen.jandan.data.dao.SimplePostDao;
import info.xudshen.jandan.data.repository.datasource.PostDataStore;
import info.xudshen.jandan.domain.model.SimplePost;

/**
 * Created by xudshen on 16/2/16.
 */
@Singleton
public class PostDataStoreFactory {
    private final PostDao postDao;
    private final SimplePostDao simplePostDao;
    private final MetaDao metaDao;
    private final CommentDao commentDao;

    @Inject
    public PostDataStoreFactory(PostDao postDao, SimplePostDao simplePostDao, MetaDao metaDao, CommentDao commentDao) {
        if (postDao == null || simplePostDao == null || metaDao == null || commentDao == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.postDao = postDao;
        this.simplePostDao = simplePostDao;
        this.metaDao = metaDao;
        this.commentDao = commentDao;
    }

    /**
     * @param postId used for check
     * @return
     */
    public PostDataStore create(Long postId) {
        PostDataStore postDataStore;

        if (this.postDao.queryBuilder().where(PostDao.Properties.PostId.eq(postId))
                .buildCount().forCurrentThread().count() > 0) {
            postDataStore = new LocalPostDataStore(postDao);
        } else {
            postDataStore = new CloudPostDataStore(ApiAdapter.getPostService(),
                    postDao, simplePostDao, metaDao, commentDao);
        }

        return postDataStore;
    }

    public PostDataStore createCloudDataStore() {
        return new CloudPostDataStore(ApiAdapter.getPostService(),
                postDao, simplePostDao, metaDao, commentDao);
    }
}

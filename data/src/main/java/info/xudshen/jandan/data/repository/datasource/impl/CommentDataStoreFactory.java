package info.xudshen.jandan.data.repository.datasource.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import info.xudshen.jandan.data.api.ApiAdapter;
import info.xudshen.jandan.data.dao.DuoshuoCommentDao;
import info.xudshen.jandan.data.dao.MetaDao;
import info.xudshen.jandan.data.repository.datasource.CommentDataStore;

/**
 * Created by xudshen on 16/2/20.
 */
@Singleton
public class CommentDataStoreFactory {
    private final DuoshuoCommentDao duoshuoCommentDao;
    private final MetaDao metaDao;

    @Inject
    public CommentDataStoreFactory(DuoshuoCommentDao duoshuoCommentDao, MetaDao metaDao) {
        if (duoshuoCommentDao == null || metaDao == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.duoshuoCommentDao = duoshuoCommentDao;
        this.metaDao = metaDao;
    }

    public CommentDataStore createCloudDataStore() {
        return new CloudCommentDataStore(ApiAdapter.getCommentService(),
                duoshuoCommentDao, metaDao);
    }
}

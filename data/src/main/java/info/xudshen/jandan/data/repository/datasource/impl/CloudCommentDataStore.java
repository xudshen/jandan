package info.xudshen.jandan.data.repository.datasource.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import info.xudshen.jandan.data.api.ICommentService;
import info.xudshen.jandan.data.api.response.CommentListResponse;
import info.xudshen.jandan.data.constants.Constants;
import info.xudshen.jandan.data.dao.DuoshuoCommentDao;
import info.xudshen.jandan.data.dao.MetaDao;
import info.xudshen.jandan.data.repository.datasource.CommentDataStore;
import info.xudshen.jandan.domain.model.DuoshuoComment;
import info.xudshen.jandan.domain.model.Meta;
import rx.Observable;

/**
 * Created by xudshen on 16/2/22.
 */
public class CloudCommentDataStore implements CommentDataStore {
    private static final Logger logger = LoggerFactory.getLogger(CloudCommentDataStore.class);

    private final ICommentService commentService;
    private final DuoshuoCommentDao duoshuoCommentDao;
    private final MetaDao metaDao;

    public CloudCommentDataStore(ICommentService commentService, DuoshuoCommentDao duoshuoCommentDao, MetaDao metaDao) {
        this.commentService = commentService;
        this.duoshuoCommentDao = duoshuoCommentDao;
        this.metaDao = metaDao;
    }

    @Override
    public Observable<List<DuoshuoComment>> commentList(String threadKey) {
        return this.commentService.getDuoshuoCommentList(threadKey, 1l, Constants.PAGE_SIZE)
                .map(commentListResponse -> {
                    List<DuoshuoComment> duoshuoComments = new ArrayList<DuoshuoComment>();
                    for (CommentListResponse.DuoshuoCommentWrapper wrapper : commentListResponse.getCommentList()) {
                        DuoshuoComment duoshuoComment = wrapper.getDuoshuoComment();
                        duoshuoComment.setThreadKey(threadKey);
                        duoshuoComments.add(duoshuoComment);
                    }
                    return duoshuoComments;
                })
                .doOnNext(duoshuoComments -> {
                    CloudCommentDataStore.this.duoshuoCommentDao.queryBuilder()
                            .where(DuoshuoCommentDao.Properties.ThreadKey.eq(threadKey))
                            .buildDelete().executeDeleteWithoutDetachingEntities();
                    CloudCommentDataStore.this.duoshuoCommentDao.insertOrReplaceInTx(duoshuoComments);
                })
                .doOnCompleted(() -> {
                    Meta commentPage = DataStoreHelper.getMeta(this.metaDao, threadKey);
                    commentPage.setLongValue(1l);
                    this.metaDao.update(commentPage);
                });
    }

    @Override
    public Observable<List<DuoshuoComment>> commentListNext(String threadKey) {
        Meta commentPage = DataStoreHelper.getMeta(this.metaDao, threadKey);
        return this.commentService.getDuoshuoCommentList(threadKey, commentPage.getLongValue() + 1, Constants.PAGE_SIZE)
                .map(commentListResponse -> {
                    List<DuoshuoComment> duoshuoComments = new ArrayList<DuoshuoComment>();
                    for (CommentListResponse.DuoshuoCommentWrapper wrapper : commentListResponse.getCommentList()) {
                        DuoshuoComment duoshuoComment = wrapper.getDuoshuoComment();
                        duoshuoComment.setThreadKey(threadKey);
                        duoshuoComments.add(duoshuoComment);
                    }
                    return duoshuoComments;
                })
                .map(duoshuoComments -> {
                    //TODO:optimize this & not(?) delete old data?
                    List<DuoshuoComment> newComments = new ArrayList<DuoshuoComment>();
                    for (DuoshuoComment duoshuoComment : duoshuoComments) {
                        if (CloudCommentDataStore.this.duoshuoCommentDao.queryBuilder()
                                .where(DuoshuoCommentDao.Properties.CommentId.eq(duoshuoComment.getCommentId()))
                                .buildCount().forCurrentThread().count() == 0) {
                            newComments.add(duoshuoComment);
                        }
                    }
                    CloudCommentDataStore.this.duoshuoCommentDao.insertOrReplaceInTx(newComments);
                    return newComments;
                })
                .doOnNext(duoshuoComments -> {
                    if (duoshuoComments.size() == Constants.PAGE_SIZE) {
                        commentPage.setLongValue(commentPage.getLongValue() + 1);
                        this.metaDao.update(commentPage);
                    }
                });
    }
}

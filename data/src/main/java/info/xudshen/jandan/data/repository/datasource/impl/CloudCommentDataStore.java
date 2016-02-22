package info.xudshen.jandan.data.repository.datasource.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import info.xudshen.jandan.data.api.ICommentService;
import info.xudshen.jandan.data.api.response.CommentListResponse;
import info.xudshen.jandan.data.constants.Constants;
import info.xudshen.jandan.data.dao.MetaDao;
import info.xudshen.jandan.data.dao.PicCommentDao;
import info.xudshen.jandan.data.repository.datasource.CommentDataStore;
import info.xudshen.jandan.domain.model.Meta;
import info.xudshen.jandan.domain.model.PicComment;
import rx.Observable;

/**
 * Created by xudshen on 16/2/22.
 */
public class CloudCommentDataStore implements CommentDataStore {
    private static final Logger logger = LoggerFactory.getLogger(CloudCommentDataStore.class);

    private final ICommentService commentService;
    private final PicCommentDao picCommentDao;
    private final MetaDao metaDao;

    public CloudCommentDataStore(ICommentService commentService, PicCommentDao picCommentDao, MetaDao metaDao) {
        this.commentService = commentService;
        this.picCommentDao = picCommentDao;
        this.metaDao = metaDao;
    }

    @Override
    public Observable<List<PicComment>> picCommentList(String threadKey) {
        return this.commentService.getPicCommentList(threadKey, 1l, Constants.PAGE_SIZE)
                .map(commentListResponse -> {
                    List<PicComment> picComments = new ArrayList<PicComment>();
                    for (String key : commentListResponse.getParentPosts().keySet()) {
                        CommentListResponse.PicCommentWrapper wrapper =
                                commentListResponse.getParentPosts().get(key);
                        PicComment picComment = wrapper.getPicComment();
                        picComment.setPicThreadKey(threadKey);
                        picComments.add(picComment);
                    }
                    return picComments;
                })
                .doOnNext(picComments -> {
                    CloudCommentDataStore.this.picCommentDao.queryBuilder()
                            .where(PicCommentDao.Properties.PicThreadKey.eq(threadKey))
                            .buildDelete().executeDeleteWithoutDetachingEntities();
                    CloudCommentDataStore.this.picCommentDao.insertOrReplaceInTx(picComments);
                })
                .doOnCompleted(() -> {
                    Meta commentPage = DataStoreHelper.getMeta(this.metaDao, threadKey);
                    commentPage.setLongValue(1l);
                    this.metaDao.update(commentPage);
                });
    }

    @Override
    public Observable<List<PicComment>> picCommentListNext(String threadKey) {
        Meta commentPage = DataStoreHelper.getMeta(this.metaDao, threadKey);
        return this.commentService.getPicCommentList(threadKey, commentPage.getLongValue() + 1, Constants.PAGE_SIZE)
                .map(commentListResponse -> {
                    List<PicComment> picComments = new ArrayList<PicComment>();
                    for (String key : commentListResponse.getParentPosts().keySet()) {
                        CommentListResponse.PicCommentWrapper wrapper =
                                commentListResponse.getParentPosts().get(key);
                        PicComment picComment = wrapper.getPicComment();
                        picComment.setPicThreadKey(threadKey);
                        picComments.add(picComment);
                    }
                    return picComments;
                })
                .doOnNext(picComments -> {
                    if (commentPage.getLongValue() + 1 == 1) {
                        CloudCommentDataStore.this.picCommentDao.queryBuilder()
                                .where(PicCommentDao.Properties.PicThreadKey.eq(threadKey))
                                .buildDelete().executeDeleteWithoutDetachingEntities();
                    }
                    CloudCommentDataStore.this.picCommentDao.insertOrReplaceInTx(picComments);
                })
                .doOnNext(picComments -> {
                    if (picComments.size() == Constants.PAGE_SIZE) {
                        commentPage.setLongValue(commentPage.getLongValue() + 1);
                        this.metaDao.update(commentPage);
                    }
                });
    }
}

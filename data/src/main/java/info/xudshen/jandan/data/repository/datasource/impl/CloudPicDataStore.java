package info.xudshen.jandan.data.repository.datasource.impl;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import info.xudshen.jandan.data.api.ICommentService;
import info.xudshen.jandan.data.api.IPicService;
import info.xudshen.jandan.data.api.response.CommentCountResponse;
import info.xudshen.jandan.data.api.response.PicListResponse;
import info.xudshen.jandan.data.dao.MetaDao;
import info.xudshen.jandan.data.dao.PicItemDao;
import info.xudshen.jandan.data.repository.datasource.PicDataStore;
import info.xudshen.jandan.domain.model.Meta;
import info.xudshen.jandan.domain.model.PicItem;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by xudshen on 16/2/20.
 */
public class CloudPicDataStore implements PicDataStore {
    private static final Logger logger = LoggerFactory.getLogger(CloudPicDataStore.class);

    private final IPicService picService;
    private final ICommentService commentService;
    private final PicItemDao picItemDao;
    private final MetaDao metaDao;

    private Action1<List<PicItem>> refreshComment;

    public CloudPicDataStore(IPicService picService, ICommentService commentService, PicItemDao picItemDao, MetaDao metaDao) {
        this.picService = picService;
        this.commentService = commentService;
        this.picItemDao = picItemDao;
        this.metaDao = metaDao;

        this.refreshComment = picItems -> {
            commentService.getPicCommentCount(Joiner.on(",").join(Lists.transform(picItems,
                    picItem -> "comment-" + picItem.getPicId())))
                    .subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                    .subscribe(commentCountResponse -> {
                        //TODO: optimize this with tx
                        List<PicItem> picItemList = new ArrayList<PicItem>();
                        for (String key : commentCountResponse.getResponse().keySet()) {
                            CommentCountResponse.CommentCount commentCount = commentCountResponse.getResponse().get(key);
                            Long picId = commentCount.getPicId();
                            logger.info("{}:{}", commentCount.getThreadKey(), commentCount.getCount());
                            PicItem picItem = CloudPicDataStore.this.picItemDao.queryBuilder().where(PicItemDao.Properties.PicId.eq(picId)).unique();
                            if (picItem != null) {
                                picItem.setCommentCount(commentCount.getCount());
                                picItem.setCommentThreadId(commentCount.getThreadId());
                                picItemList.add(picItem);
                            }
                        }
                        if (picItemList.size() > 0)
                            CloudPicDataStore.this.picItemDao.updateInTx(picItemList);
                    });
        };
    }

    @Override
    public Observable<PicItem> pic(Long picId) {
        throw new NotImplementedException("");
    }


    @Override
    public Observable<List<PicItem>> picList() {
        return this.picService.getPicListAsync(1l)
                .map(picListResponse -> {
                    List<PicItem> picItems = new ArrayList<PicItem>();
                    for (PicListResponse.PicItemWrapper wrapper : picListResponse.getList()) {
                        picItems.add(wrapper.getPicItem());
                    }
                    return picItems;
                })
                .doOnNext(picItems -> {
                    CloudPicDataStore.this.picItemDao.deleteAll();
                    CloudPicDataStore.this.picItemDao.insertOrReplaceInTx(picItems);
                })
                .doOnNext(refreshComment)
                .doOnCompleted(() -> {
                    Meta picPage = DataStoreHelper.getMeta(this.metaDao, DataStoreHelper.PIC_KEY);
                    picPage.setLongValue(1l);
                    this.metaDao.update(picPage);
                });
    }

    @Override
    public Observable<List<PicItem>> picListNext() {
        Meta picPage = DataStoreHelper.getMeta(this.metaDao, DataStoreHelper.PIC_KEY);
        return this.picService.getPicListAsync(picPage.getLongValue() + 1)
                .map(picListResponse -> {
                    List<PicItem> picItems = new ArrayList<PicItem>();
                    for (PicListResponse.PicItemWrapper wrapper : picListResponse.getList()) {
                        picItems.add(wrapper.getPicItem());
                    }
                    return picItems;
                })
                .doOnNext(picItems -> {
                    if (picPage.getLongValue() + 1 == 1) {
                        //TODO: not right
                        CloudPicDataStore.this.picItemDao.deleteAll();
                    }
                    CloudPicDataStore.this.picItemDao.insertOrReplaceInTx(picItems);
                })
                .doOnNext(refreshComment)
                .doOnCompleted(() -> {
                    picPage.setLongValue(picPage.getLongValue() + 1);
                    this.metaDao.update(picPage);
                });
    }
}

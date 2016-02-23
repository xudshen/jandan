package info.xudshen.jandan.data.repository.datasource.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import info.xudshen.jandan.data.api.ICommentService;
import info.xudshen.jandan.data.api.ICommonItemService;
import info.xudshen.jandan.data.api.response.CommentCountResponse;
import info.xudshen.jandan.data.api.response.JokeListResponse;
import info.xudshen.jandan.data.constants.Constants;
import info.xudshen.jandan.data.dao.JokeItemDao;
import info.xudshen.jandan.data.dao.MetaDao;
import info.xudshen.jandan.data.repository.datasource.JokeDataStore;
import info.xudshen.jandan.domain.model.JokeItem;
import info.xudshen.jandan.domain.model.Meta;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by xudshen on 16/2/20.
 */
public class CloudJokeDataStore implements JokeDataStore {
    private static final Logger logger = LoggerFactory.getLogger(CloudJokeDataStore.class);

    private final ICommonItemService commonItemService;
    private final ICommentService commentService;
    private final JokeItemDao jokeItemDao;
    private final MetaDao metaDao;

    private Action1<List<JokeItem>> refreshComment;

    public CloudJokeDataStore(ICommonItemService commonItemService, ICommentService commentService, JokeItemDao jokeItemDao, MetaDao metaDao) {
        this.commonItemService = commonItemService;
        this.commentService = commentService;
        this.jokeItemDao = jokeItemDao;
        this.metaDao = metaDao;

        this.refreshComment = jokeItems -> {
            CloudJokeDataStore.this.commentService.getDuoshuoCommentList(Joiner.on(",").join(Lists.transform(jokeItems,
                    jokeItem -> Constants.THREAD_PREFIX + jokeItem.getJokeId())))
                    .subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                    .subscribe(commentCountResponse -> {
                        //TODO: optimize this with tx
                        List<JokeItem> jokeItemList = new ArrayList<JokeItem>();
                        for (String key : commentCountResponse.getResponse().keySet()) {
                            CommentCountResponse.CommentCount commentCount = commentCountResponse.getResponse().get(key);
                            Long jokeId = commentCount.getCommonItemId();
                            logger.info("{}:{}", commentCount.getThreadKey(), commentCount.getCount());
                            JokeItem jokeItem = CloudJokeDataStore.this.jokeItemDao.queryBuilder().where(JokeItemDao.Properties.JokeId.eq(jokeId)).unique();
                            if (jokeItem != null) {
                                jokeItem.setCommentCount(commentCount.getCount());
                                jokeItem.setThreadId(commentCount.getThreadId());
                                jokeItemList.add(jokeItem);
                            }
                        }
                        if (jokeItemList.size() > 0)
                            CloudJokeDataStore.this.jokeItemDao.updateInTx(jokeItemList);
                    });
        };
    }

    @Override
    public Observable<JokeItem> joke(Long jokeId) {
        throw new NotImplementedException("");
    }


    @Override
    public Observable<List<JokeItem>> jokeList() {
        return this.commonItemService.getJokeListAsync(1l)
                .map(jokeListResponse -> {
                    List<JokeItem> jokeItems = new ArrayList<JokeItem>();
                    for (JokeListResponse.JokeItemWrapper wrapper : jokeListResponse.getList()) {
                        jokeItems.add(wrapper.getJokeItem());
                    }
                    return jokeItems;
                })
                .doOnNext(jokeItems -> {
                    CloudJokeDataStore.this.jokeItemDao.deleteAll();
                    CloudJokeDataStore.this.jokeItemDao.insertOrReplaceInTx(jokeItems);
                })
                .doOnNext(refreshComment)
                .doOnCompleted(() -> {
                    Meta jokePage = DataStoreHelper.getMeta(this.metaDao, DataStoreHelper.JOKE_KEY);
                    jokePage.setLongValue(1l);
                    this.metaDao.update(jokePage);
                });
    }

    @Override
    public Observable<List<JokeItem>> jokeListNext() {
        Meta jokePage = DataStoreHelper.getMeta(this.metaDao, DataStoreHelper.JOKE_KEY);
        return this.commonItemService.getJokeListAsync(jokePage.getLongValue() + 1)
                .map(jokeListResponse -> {
                    List<JokeItem> jokeItems = new ArrayList<JokeItem>();
                    for (JokeListResponse.JokeItemWrapper wrapper : jokeListResponse.getList()) {
                        jokeItems.add(wrapper.getJokeItem());
                    }
                    return jokeItems;
                })
                .doOnNext(jokeItems -> {
                    if (jokePage.getLongValue() + 1 == 1) {
                        //TODO: not right
                        CloudJokeDataStore.this.jokeItemDao.deleteAll();
                    }
                    CloudJokeDataStore.this.jokeItemDao.insertOrReplaceInTx(jokeItems);
                })
                .doOnNext(refreshComment)
                .doOnCompleted(() -> {
                    jokePage.setLongValue(jokePage.getLongValue() + 1);
                    this.metaDao.update(jokePage);
                });
    }
}

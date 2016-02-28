package info.xudshen.jandan.data.repository.datasource.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.xudshen.jandan.data.api.ICommentService;
import info.xudshen.jandan.data.api.ICommonItemService;
import info.xudshen.jandan.data.api.response.CommentCountResponse;
import info.xudshen.jandan.data.api.response.VideoListResponse;
import info.xudshen.jandan.data.constants.Constants;
import info.xudshen.jandan.data.dao.JokeItemDao;
import info.xudshen.jandan.data.dao.MetaDao;
import info.xudshen.jandan.data.dao.VideoItemDao;
import info.xudshen.jandan.data.repository.datasource.VideoDataStore;
import info.xudshen.jandan.domain.enums.VoteResult;
import info.xudshen.jandan.domain.enums.VoteType;
import info.xudshen.jandan.domain.model.JokeItem;
import info.xudshen.jandan.domain.model.Meta;
import info.xudshen.jandan.domain.model.VideoItem;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by xudshen on 16/2/20.
 */
public class CloudVideoDataStore implements VideoDataStore {
    private static final Logger logger = LoggerFactory.getLogger(CloudVideoDataStore.class);

    private final ICommonItemService videoService;
    private final ICommentService commentService;
    private final VideoItemDao videoItemDao;
    private final MetaDao metaDao;

    private Action1<List<VideoItem>> refreshComment;

    public CloudVideoDataStore(ICommonItemService videoService, ICommentService commentService, VideoItemDao videoItemDao, MetaDao metaDao) {
        this.videoService = videoService;
        this.commentService = commentService;
        this.videoItemDao = videoItemDao;
        this.metaDao = metaDao;

        this.refreshComment = videoItems -> {
            CloudVideoDataStore.this.commentService.getDuoshuoCommentList(Joiner.on(",").join(Lists.transform(videoItems,
                    videoItem -> Constants.THREAD_PREFIX + videoItem.getVideoId())))
                    .subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                    .subscribe(commentCountResponse -> {
                        //TODO: optimize this with tx
                        List<VideoItem> videoItemList = new ArrayList<VideoItem>();
                        for (String key : commentCountResponse.getResponse().keySet()) {
                            CommentCountResponse.CommentCount commentCount = commentCountResponse.getResponse().get(key);
                            Long videoId = commentCount.getCommonItemId();
                            logger.info("{}:{}", commentCount.getThreadKey(), commentCount.getCount());
                            VideoItem videoItem = CloudVideoDataStore.this.videoItemDao.queryBuilder().where(VideoItemDao.Properties.VideoId.eq(videoId)).unique();
                            if (videoItem != null) {
                                videoItem.setCommentCount(commentCount.getCount());
                                videoItem.setThreadId(commentCount.getThreadId());
                                videoItemList.add(videoItem);
                            }
                        }
                        if (videoItemList.size() > 0)
                            CloudVideoDataStore.this.videoItemDao.updateInTx(videoItemList);
                    }, throwable -> {
                    });
        };
    }

    @Override
    public Observable<VideoItem> video(Long videoId) {
        throw new NotImplementedException("");
    }


    @Override
    public Observable<List<VideoItem>> videoList() {
        return this.videoService.getVideoListAsync(1l)
                .map(videoListResponse -> {
                    List<VideoItem> videoItems = new ArrayList<VideoItem>();
                    for (VideoListResponse.VideoItemWrapper wrapper : videoListResponse.getList()) {
                        videoItems.add(wrapper.getVideoItem());
                    }
                    return videoItems;
                })
                .doOnNext(videoItems -> {
                    CloudVideoDataStore.this.videoItemDao.deleteAll();
                    CloudVideoDataStore.this.videoItemDao.insertOrReplaceInTx(videoItems);
                })
                .doOnNext(refreshComment)
                .doOnCompleted(() -> {
                    Meta videoPage = DataStoreHelper.getMeta(this.metaDao, DataStoreHelper.VIDEO_KEY);
                    videoPage.setLongValue(1l);
                    this.metaDao.update(videoPage);
                });
    }

    @Override
    public Observable<List<VideoItem>> videoListNext() {
        Meta videoPage = DataStoreHelper.getMeta(this.metaDao, DataStoreHelper.VIDEO_KEY);
        return this.videoService.getVideoListAsync(videoPage.getLongValue() + 1)
                .map(videoListResponse -> {
                    List<VideoItem> videoItems = new ArrayList<VideoItem>();
                    for (VideoListResponse.VideoItemWrapper wrapper : videoListResponse.getList()) {
                        videoItems.add(wrapper.getVideoItem());
                    }
                    return videoItems;
                })
                .doOnNext(videoItems -> {
                    if (videoPage.getLongValue() + 1 == 1) {
                        //TODO: not right
                        CloudVideoDataStore.this.videoItemDao.deleteAll();
                    }
                    CloudVideoDataStore.this.videoItemDao.insertOrReplaceInTx(videoItems);
                })
                .doOnNext(refreshComment)
                .doOnCompleted(() -> {
                    videoPage.setLongValue(videoPage.getLongValue() + 1);
                    this.metaDao.update(videoPage);
                });
    }

    @Override
    public Observable<VoteResult> voteCommonItem(Long commentId, VoteType voteType) {
        return this.videoService.voteCommonItem(voteType == VoteType.OO ? 1 : 0, commentId)
                .map(responseBody -> {
                    try {
                        return VoteResult.fromString(responseBody.string(), voteType);
                    } catch (IOException e) {
                        return VoteResult.Voted;
                    }
                }).doOnNext(voteResult -> {
                    if (voteResult == VoteResult.Thanks) {
                        VideoItem videoItem = CloudVideoDataStore.this.videoItemDao.queryBuilder()
                                .where(VideoItemDao.Properties.VideoId.eq(commentId))
                                .build().forCurrentThread().unique();
                        if (videoItem != null) {
                            if (voteType == VoteType.OO) {
                                videoItem.setVotePositive(videoItem.getVotePositive() + 1);
                                CloudVideoDataStore.this.videoItemDao.update(videoItem);
                            } else if (voteType == VoteType.XX) {
                                videoItem.setVoteNegative(videoItem.getVoteNegative() + 1);
                                CloudVideoDataStore.this.videoItemDao.update(videoItem);
                            }
                        }
                    }
                });
    }
}

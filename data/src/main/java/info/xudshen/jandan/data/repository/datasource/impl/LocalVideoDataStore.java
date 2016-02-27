package info.xudshen.jandan.data.repository.datasource.impl;

import org.apache.commons.lang3.NotImplementedException;

import java.util.List;

import info.xudshen.jandan.data.dao.VideoItemDao;
import info.xudshen.jandan.data.exception.VideoNotFoundException;
import info.xudshen.jandan.data.repository.datasource.VideoDataStore;
import info.xudshen.jandan.domain.enums.VoteResult;
import info.xudshen.jandan.domain.enums.VoteType;
import info.xudshen.jandan.domain.model.VideoItem;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by xudshen on 16/2/16.
 */
public class LocalVideoDataStore implements VideoDataStore {
    private final VideoItemDao videoItemDao;

    public LocalVideoDataStore(VideoItemDao videoItemDao) {
        this.videoItemDao = videoItemDao;
    }

    @Override
    public Observable<VideoItem> video(Long videoId) {
        return Observable.create(new Observable.OnSubscribe<VideoItem>() {
            @Override
            public void call(Subscriber<? super VideoItem> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    VideoItem videoItem = LocalVideoDataStore.this.videoItemDao.queryBuilder()
                            .where(VideoItemDao.Properties.VideoId.eq(videoId))
                            .build().forCurrentThread().unique();
                    if (videoItem == null) {
                        subscriber.onError(new VideoNotFoundException());
                    } else {
                        subscriber.onNext(videoItem);
                        subscriber.onCompleted();
                    }
                }
            }
        });
    }

    @Override
    public Observable<List<VideoItem>> videoList() {
        throw new NotImplementedException("");
    }

    @Override
    public Observable<List<VideoItem>> videoListNext() {
        throw new NotImplementedException("");
    }

    @Override
    public Observable<VoteResult> voteCommonItem(Long commentId, VoteType voteType) {
        throw new NotImplementedException("");
    }
}
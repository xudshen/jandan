package info.xudshen.jandan.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import info.xudshen.jandan.data.repository.datasource.impl.VideoDataStoreFactory;
import info.xudshen.jandan.domain.model.VideoItem;
import info.xudshen.jandan.domain.repository.VideoRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/20.
 */
@Singleton
public class VideoDataRepository implements VideoRepository {
    private final VideoDataStoreFactory videoDataStoreFactory;

    @Inject
    public VideoDataRepository(VideoDataStoreFactory videoDataStoreFactory) {
        this.videoDataStoreFactory = videoDataStoreFactory;
    }

    @Override
    public Observable<VideoItem> video(Long videoId) {
        return this.videoDataStoreFactory.createLocalCloudDataStore().video(videoId);
    }

    @Override
    public Observable<List<VideoItem>> videoList() {
        return this.videoDataStoreFactory.createCloudDataStore().videoList();
    }

    @Override
    public Observable<List<VideoItem>> videoListNextPage() {
        return this.videoDataStoreFactory.createCloudDataStore().videoListNext();
    }
}

package info.xudshen.jandan.data.repository.datasource.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import info.xudshen.jandan.data.api.ApiAdapter;
import info.xudshen.jandan.data.dao.MetaDao;
import info.xudshen.jandan.data.dao.VideoItemDao;
import info.xudshen.jandan.data.repository.datasource.VideoDataStore;

/**
 * Created by xudshen on 16/2/20.
 */
@Singleton
public class VideoDataStoreFactory {
    private final VideoItemDao videoItemDao;
    private final MetaDao metaDao;

    @Inject
    public VideoDataStoreFactory(VideoItemDao videoItemDao, MetaDao metaDao) {
        if (videoItemDao == null || metaDao == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.videoItemDao = videoItemDao;
        this.metaDao = metaDao;
    }

    public VideoDataStore createLocalCloudDataStore() {
        VideoDataStore videoDataStore = new LocalVideoDataStore(videoItemDao);
        return videoDataStore;
    }


    public VideoDataStore createCloudDataStore() {
        return new CloudVideoDataStore(ApiAdapter.getCommonItemService(), ApiAdapter.getCommentService(),
                videoItemDao, metaDao);
    }
}

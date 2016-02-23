package info.xudshen.jandan.data.repository.datasource;

import java.util.List;

import info.xudshen.jandan.domain.model.VideoItem;
import rx.Observable;

/**
 * Created by xudshen on 16/2/20.
 */
public interface VideoDataStore {
    Observable<VideoItem> video(Long videoId);

    Observable<List<VideoItem>> videoList();

    Observable<List<VideoItem>> videoListNext();
}

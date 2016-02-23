package info.xudshen.jandan.domain.repository;

import java.util.List;

import info.xudshen.jandan.domain.model.VideoItem;
import rx.Observable;

/**
 * Created by xudshen on 16/2/20.
 */
public interface VideoRepository {
    Observable<VideoItem> video(Long videoId);

    Observable<List<VideoItem>> videoList();

    Observable<List<VideoItem>> videoListNextPage();
}

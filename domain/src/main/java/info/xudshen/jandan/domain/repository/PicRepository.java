package info.xudshen.jandan.domain.repository;

import java.util.List;

import info.xudshen.jandan.domain.model.PicItem;
import rx.Observable;

/**
 * Created by xudshen on 16/2/20.
 */
public interface PicRepository {
    Observable<List<PicItem>> picList();

    Observable<List<PicItem>> picListNextPage();
}

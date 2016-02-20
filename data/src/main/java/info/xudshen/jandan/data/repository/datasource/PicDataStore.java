package info.xudshen.jandan.data.repository.datasource;

import java.util.List;

import info.xudshen.jandan.domain.model.PicItem;
import rx.Observable;

/**
 * Created by xudshen on 16/2/20.
 */
public interface PicDataStore {
    Observable<List<PicItem>> picList();

    Observable<List<PicItem>> picListNext();
}

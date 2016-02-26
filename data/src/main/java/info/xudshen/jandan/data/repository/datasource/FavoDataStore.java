package info.xudshen.jandan.data.repository.datasource;

import java.util.List;

import info.xudshen.jandan.domain.enums.ReaderItemType;
import info.xudshen.jandan.domain.model.FavoItem;
import rx.Observable;

/**
 * Created by xudshen on 16/2/25.
 */
public interface FavoDataStore {
    Observable<List<FavoItem>> favoItemList();

    Observable<FavoItem> favoItem(ReaderItemType type, Long actualId);

    Observable<Boolean> saveFavoItem(FavoItem favoItem);

    Observable<Boolean> deleteFavoItem(ReaderItemType type, String actualId);
}

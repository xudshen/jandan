package info.xudshen.jandan.view.adapter;

import info.xudshen.jandan.domain.enums.ReaderItemType;

/**
 * Created by xudshen on 16/2/24.
 */
public interface IItemInfo {
    String getAdapterItemId(int position);

    ReaderItemType getAdapterItemType(int position);
}

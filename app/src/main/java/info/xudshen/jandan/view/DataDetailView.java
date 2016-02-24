package info.xudshen.jandan.view;

/**
 * Created by xudshen on 16/2/16.
 */
public interface DataDetailView<T> extends LoadDataView {
    void showLoadingMore();

    void hideLoadingMore(int count);

    void renderItemDetail(T item);
}

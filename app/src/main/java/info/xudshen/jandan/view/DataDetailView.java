package info.xudshen.jandan.view;

/**
 * Created by xudshen on 16/2/16.
 */
public interface DataDetailView<T> extends LoadDataView {
    void showLoadingMore();

    /**
     * @param count show newly loaded item count
     */
    void hideLoadingMore(int count);

    void renderDataDetail(T item);
}

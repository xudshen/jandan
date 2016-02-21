package info.xudshen.jandan.view;

/**
 * Created by xudshen on 16/2/16.
 */
public interface DataDetailView<T> extends LoadDataView {
    void showSwipeUpLoading();

    void hideSwipeUpLoading();

    void noMoreComments();

    void renderItemDetail(T item);
}

package info.xudshen.jandan.view;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a user profile.
 */
public interface DataListView extends LoadDataView {
    void showLoadingMore();

    void hideLoadingMore();

    void renderDataList();
}

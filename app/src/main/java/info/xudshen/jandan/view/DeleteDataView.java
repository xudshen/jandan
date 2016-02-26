package info.xudshen.jandan.view;

import rx.Observable;

/**
 * Created by xudshen on 16/2/26.
 */
public interface DeleteDataView {
    void deletingData();

    void result(boolean success);

    /**
     * Get rxlifecycle
     */
    <T> Observable.Transformer<T, T> bindToLifecycle();
}

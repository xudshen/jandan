package info.xudshen.jandan.view;

import rx.Observable;

/**
 * Created by xudshen on 16/2/26.
 */
public interface SaveDataView {
    void savingData();

    void result(boolean success);

    <T> Observable.Transformer<T, T> bindToLifecycle();
}

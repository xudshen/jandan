package info.xudshen.droiddata.dao;

/**
 * Created by xudshen on 15/10/26.
 */
public interface IModelObservable<T> {
    Long getModelKey();

    void refresh(T entity);
}

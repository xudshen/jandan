package info.xudshen.droiddata.dao;

/**
 * Created by xudshen on 15/10/27.
 */
public interface IModelTrans<T, TO extends IModelObservable> {
    TO to(T entity);
}

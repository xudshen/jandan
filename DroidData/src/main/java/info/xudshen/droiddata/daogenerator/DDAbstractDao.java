package info.xudshen.droiddata.daogenerator;

import java.util.Arrays;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.DeleteQuery;

/**
 * Created by xudshen on 15/10/9.
 */
public abstract class DDAbstractDao<T, K extends Long> extends AbstractDao<T, K> {
    public DDAbstractDao(DaoConfig config) {
        super(config);
    }

    public DDAbstractDao(DaoConfig config, AbstractDaoSession daoSession) {
        super(config, daoSession);
    }

    //<editor-fold desc="notifyInsert">
    protected abstract void notifyInsert(T entity);

    protected abstract void notifyInsert(Iterable<T> entities);
    //{
//        context.getContentResolver().insert(ContentUris.withAppendedId(CONTENT_URI, pk.longValue()), null);
//    }

    @Override
    public long insert(T entity) {
        long rowId = super.insert(entity);
        notifyInsert(entity);
        return rowId;
    }

    @Override
    public void insertInTx(Iterable<T> entities) {
        super.insertInTx(entities);
        notifyInsert(entities);
    }

    @Override
    public void insertInTx(T... entities) {
        super.insertInTx(entities);
        notifyInsert(Arrays.asList(entities));
    }

    /**
     * may cause not able to notifyInsert
     */
    @Override
    @Deprecated
    public void insertInTx(Iterable<T> entities, boolean setPrimaryKey) {
        super.insertInTx(entities, setPrimaryKey);
        notifyInsert(entities);
    }

    @Override
    public long insertOrReplace(T entity) {
        long rowId = super.insertOrReplace(entity);
        notifyInsert(entity);
        return rowId;
    }

    @Override
    public void insertOrReplaceInTx(Iterable<T> entities) {
        super.insertOrReplaceInTx(entities);
        notifyInsert(entities);
    }

    @Override
    public void insertOrReplaceInTx(T... entities) {
        super.insertOrReplaceInTx(entities);
        notifyInsert(Arrays.asList(entities));
    }

    /**
     * may cause not able to notifyInsert
     */
    @Override
    @Deprecated
    public void insertOrReplaceInTx(Iterable<T> entities, boolean setPrimaryKey) {
        super.insertOrReplaceInTx(entities, setPrimaryKey);
        notifyInsert(entities);
    }

    /**
     * may cause not able to notifyInsert
     */
    @Override
    @Deprecated
    public long insertWithoutSettingPk(T entity) {
        return super.insertWithoutSettingPk(entity);
    }
    //</editor-fold>

    //<editor-fold desc="notifyUpdate">
    protected abstract void notifyUpdate(T entity);

    protected abstract void notifyUpdate(Iterable<T> entities);

    @Override
    public void update(T entity) {
        super.update(entity);
        notifyUpdate(entity);
    }

    @Override
    public void updateInTx(Iterable<T> entities) {
        super.updateInTx(entities);
        notifyUpdate(entities);
    }

    @Override
    public void updateInTx(T... entities) {
        super.updateInTx(entities);
        notifyUpdate(Arrays.asList(entities));
    }
    //</editor-fold>

    //<editor-fold desc="notifyDelete">
    protected abstract void notifyDeleteByKey(K key);

    protected abstract void notifyDeleteByKey(Iterable<K> keys);

    protected abstract void notifyDelete(T entity);

    protected abstract void notifyDelete(Iterable<T> entities);

    @Override
    public void deleteByKey(K key) {
        super.deleteByKey(key);
        notifyDeleteByKey(key);
    }

    @Override
    public void deleteByKeyInTx(Iterable<K> keys) {
        super.deleteByKeyInTx(keys);
        notifyDeleteByKey(keys);
    }

    @Override
    public void deleteByKeyInTx(K... keys) {
        super.deleteByKeyInTx(keys);
        notifyDeleteByKey(Arrays.asList(keys));
    }

    @Override
    public void delete(T entity) {
        super.delete(entity);
        notifyDelete(entity);
    }

    @Override
    public void deleteInTx(Iterable<T> entities) {
        super.deleteInTx(entities);
        notifyDelete(entities);
    }

    @Override
    public void deleteInTx(T... entities) {
        super.deleteInTx(entities);
        notifyDelete(Arrays.asList(entities));
    }

    @Override
    public void deleteAll() {
        super.deleteAll();
        //TODO
    }
    //</editor-fold>

    public void delete(DeleteQuery<T> query) {
        query.executeDeleteWithoutDetachingEntities();
        notifyDeleteByKey((K) Long.valueOf(-1));
    }
}

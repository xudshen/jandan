package info.xudshen.jandan.data.dao;

import java.lang.ref.WeakReference;

import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.os.RemoteException;

import info.xudshen.droiddata.dao.DDAbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import info.xudshen.droiddata.dao.IModelObservable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import info.xudshen.jandan.domain.model.Meta;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "META".
*/
public class MetaDao extends DDAbstractDao<Meta, Long> {

    public static final String TABLENAME = "META";

    /**
     * Properties of entity Meta.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property PostPage = new Property(1, Long.class, "postPage", false, "POST_PAGE");
    }


    public MetaDao(DaoConfig config) {
        super(config);
    }
    
    public MetaDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"META\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"POST_PAGE\" INTEGER);"); // 1: postPage
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"META\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Meta entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long postPage = entity.getPostPage();
        if (postPage != null) {
            stmt.bindLong(2, postPage);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    protected Meta readEntity(Cursor cursor, int offset) {
        Meta entity = new Meta( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1) // postPage
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    protected void readEntity(Cursor cursor, Meta entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPostPage(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
    }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Meta entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Meta entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }

    public static final String AUTHORITY = "info.xudshen.jandan.data.dao.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLENAME);
    private WeakReference<Context> contextWeakReference;

    public void setContext(Context context) {
        contextWeakReference = new WeakReference<Context>(context);
    }

    @Override
    protected void notifyInsert(Meta entity) {
        Long key = getKey(entity);
        if (key != null) {
            notifyExtraOb(key);

            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().insert(
                        ContentUris.withAppendedId(CONTENT_URI, key), null);
        }
    }

    @Override
    protected void notifyInsert(Iterable<Meta> entities) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        for (Meta entity : entities) {
            Long key = getKey(entity);
            if (key != null) {
                notifyExtraOb(key);

                ops.add(ContentProviderOperation.newInsert(
                        ContentUris.withAppendedId(CONTENT_URI, key)).build());
            }
        }

        try {
            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().applyBatch(AUTHORITY, ops);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void notifyUpdate(Meta entity) {
        Long key = getKey(entity);
        if (key != null) {
            notifyExtraOb(key);

            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().update(
                        ContentUris.withAppendedId(CONTENT_URI, key), null, null, null);
        }
    }

    @Override
    protected void notifyUpdate(Iterable<Meta> entities) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        for (Meta entity : entities) {
            Long key = getKey(entity);
            if (key != null) {
                notifyExtraOb(key);

                ops.add(ContentProviderOperation.newUpdate(
                        ContentUris.withAppendedId(CONTENT_URI, key)).withValue(null, null).build());
            }
        }

        try {
            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().applyBatch(AUTHORITY, ops);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void notifyDelete(Meta entity) {
        Long key = getKey(entity);
        if (key != null) {
            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().delete(
                        ContentUris.withAppendedId(CONTENT_URI, key), null, null);
        }
    }

    @Override
    protected void notifyDelete(Iterable<Meta> entities) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        for (Meta entity : entities) {
            Long key = getKey(entity);
            if (key != null) {
                ops.add(ContentProviderOperation.newDelete(
                        ContentUris.withAppendedId(CONTENT_URI, key)).build());
            }
        }

        try {
            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().applyBatch(AUTHORITY, ops);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void notifyDeleteByKey(Long key) {
        if (key != null && contextWeakReference.get() != null) {
            if (key == -1) {
                contextWeakReference.get().getContentResolver().delete(
                        CONTENT_URI, null, null);
            } else {
                contextWeakReference.get().getContentResolver().delete(
                        ContentUris.withAppendedId(CONTENT_URI, key), null, null);
            }
        }
    }

    @Override
    protected void notifyDeleteByKey(Iterable<Long> keys) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        for (Long key : keys) {
            if (key != null) {
                ops.add(ContentProviderOperation.newDelete(
                        ContentUris.withAppendedId(CONTENT_URI, key)).build());
            }
        }

        try {
            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().applyBatch(AUTHORITY, ops);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    private Map<Long, WeakHashMap<IModelObservable<Meta>, Boolean>> extraObMap = new HashMap<>();

    /**
     * register entity to extraObMap
     */
    public void registerExtraOb(IModelObservable entity) {
        if (entity == null) return;
        if (!extraObMap.containsKey(entity.getModelKey())) {
            extraObMap.put(entity.getModelKey(), new WeakHashMap<>());
        }
        WeakHashMap<IModelObservable<Meta>, Boolean> map = extraObMap.get(entity.getModelKey());
        if (!map.containsKey(entity)) {
            map.put(entity, true);
        }
    }

    private void notifyExtraOb(Long key) {
        if (extraObMap.containsKey(key)) {
            Meta newEntity = load(key);
            for (IModelObservable<Meta> entity : extraObMap.get(key).keySet()) {
                if (entity != null)
                    entity.refresh(newEntity);
            }
        }
    }

    public Meta loadEntity(Cursor cursor){
        return this.readEntity(cursor, 0);
    }

}
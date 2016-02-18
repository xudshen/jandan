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
import info.xudshen.droiddata.dao.converter.EnumPropertyConverter;
import info.xudshen.droiddata.dao.converter.TimestampPropertyConverter;
import info.xudshen.jandan.domain.enums.ReaderItemType;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import info.xudshen.jandan.domain.model.ReadLaterItem;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "READ_LATER_ITEM".
*/
public class ReadLaterItemDao extends DDAbstractDao<ReadLaterItem, Long> {

    public static final String TABLENAME = "READ_LATER_ITEM";

    /**
     * Properties of entity ReadLaterItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ReadLaterItemId = new Property(1, Long.class, "readLaterItemId", false, "READ_LATER_ITEM_ID");
        public final static Property Type = new Property(2, String.class, "type", false, "TYPE");
        public final static Property ActualId = new Property(3, Long.class, "actualId", false, "ACTUAL_ID");
        public final static Property AddDate = new Property(4, Long.class, "addDate", false, "ADD_DATE");
    }

    private final EnumPropertyConverter<ReaderItemType> typeConverter = new EnumPropertyConverter<ReaderItemType>();
    private final TimestampPropertyConverter addDateConverter = new TimestampPropertyConverter();

    public ReadLaterItemDao(DaoConfig config) {
        super(config);
    }
    
    public ReadLaterItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"READ_LATER_ITEM\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"READ_LATER_ITEM_ID\" INTEGER," + // 1: readLaterItemId
                "\"TYPE\" TEXT," + // 2: type
                "\"ACTUAL_ID\" INTEGER," + // 3: actualId
                "\"ADD_DATE\" INTEGER);"); // 4: addDate
        // Add Indexes
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_READ_LATER_ITEM_READ_LATER_ITEM_ID ON READ_LATER_ITEM" +
                " (\"READ_LATER_ITEM_ID\");");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"READ_LATER_ITEM\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ReadLaterItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long readLaterItemId = entity.getReadLaterItemId();
        if (readLaterItemId != null) {
            stmt.bindLong(2, readLaterItemId);
        }
 
        ReaderItemType type = entity.getType();
        if (type != null) {
            stmt.bindString(3, typeConverter.convertToDatabaseValue(type));
        }
 
        Long actualId = entity.getActualId();
        if (actualId != null) {
            stmt.bindLong(4, actualId);
        }
 
        Timestamp addDate = entity.getAddDate();
        if (addDate != null) {
            stmt.bindLong(5, addDateConverter.convertToDatabaseValue(addDate));
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    protected ReadLaterItem readEntity(Cursor cursor, int offset) {
        ReadLaterItem entity = new ReadLaterItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // readLaterItemId
            cursor.isNull(offset + 2) ? null : typeConverter.convertToEntityProperty(ReaderItemType.class, cursor.getString(offset + 2)), // type
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // actualId
            cursor.isNull(offset + 4) ? null : addDateConverter.convertToEntityProperty(Timestamp.class, cursor.getLong(offset + 4)) // addDate
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    protected void readEntity(Cursor cursor, ReadLaterItem entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setReadLaterItemId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setType(cursor.isNull(offset + 2) ? null : typeConverter.convertToEntityProperty(ReaderItemType.class, cursor.getString(offset + 2)));
        entity.setActualId(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setAddDate(cursor.isNull(offset + 4) ? null : addDateConverter.convertToEntityProperty(Timestamp.class, cursor.getLong(offset + 4)));
    }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ReadLaterItem entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ReadLaterItem entity) {
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
    protected void notifyInsert(ReadLaterItem entity) {
        Long key = getKey(entity);
        if (key != null) {
            notifyExtraOb(key);

            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().insert(
                        ContentUris.withAppendedId(CONTENT_URI, key), null);
        }
    }

    @Override
    protected void notifyInsert(Iterable<ReadLaterItem> entities) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        for (ReadLaterItem entity : entities) {
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
    protected void notifyUpdate(ReadLaterItem entity) {
        Long key = getKey(entity);
        if (key != null) {
            notifyExtraOb(key);

            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().update(
                        ContentUris.withAppendedId(CONTENT_URI, key), null, null, null);
        }
    }

    @Override
    protected void notifyUpdate(Iterable<ReadLaterItem> entities) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        for (ReadLaterItem entity : entities) {
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
    protected void notifyDelete(ReadLaterItem entity) {
        Long key = getKey(entity);
        if (key != null) {
            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().delete(
                        ContentUris.withAppendedId(CONTENT_URI, key), null, null);
        }
    }

    @Override
    protected void notifyDelete(Iterable<ReadLaterItem> entities) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        for (ReadLaterItem entity : entities) {
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

    private Map<Long, WeakHashMap<IModelObservable<ReadLaterItem>, Boolean>> extraObMap = new HashMap<>();

    /**
     * register entity to extraObMap
     */
    public void registerExtraOb(IModelObservable entity) {
        if (entity == null) return;
        if (!extraObMap.containsKey(entity.getModelKey())) {
            extraObMap.put(entity.getModelKey(), new WeakHashMap<>());
        }
        WeakHashMap<IModelObservable<ReadLaterItem>, Boolean> map = extraObMap.get(entity.getModelKey());
        if (!map.containsKey(entity)) {
            map.put(entity, true);
        }
    }

    private void notifyExtraOb(Long key) {
        if (extraObMap.containsKey(key)) {
            ReadLaterItem newEntity = load(key);
            for (IModelObservable<ReadLaterItem> entity : extraObMap.get(key).keySet()) {
                if (entity != null)
                    entity.refresh(newEntity);
            }
        }
    }

    public ReadLaterItem loadEntity(Cursor cursor){
        return this.readEntity(cursor, 0);
    }

}
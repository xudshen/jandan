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
import info.xudshen.droiddata.dao.converter.TimestampPropertyConverter;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import info.xudshen.jandan.domain.model.Article;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ARTICLE".
*/
public class ArticleDao extends DDAbstractDao<Article, Long> {

    public static final String TABLENAME = "ARTICLE";

    /**
     * Properties of entity Article.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ArticleId = new Property(1, Long.class, "articleId", false, "ARTICLE_ID");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property Author = new Property(3, String.class, "author", false, "AUTHOR");
        public final static Property Time = new Property(4, Long.class, "time", false, "TIME");
        public final static Property Content = new Property(5, String.class, "content", false, "CONTENT");
    }

    private final TimestampPropertyConverter timeConverter = new TimestampPropertyConverter();

    public ArticleDao(DaoConfig config) {
        super(config);
    }
    
    public ArticleDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"ARTICLE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"ARTICLE_ID\" INTEGER," + // 1: articleId
                "\"TITLE\" TEXT," + // 2: title
                "\"AUTHOR\" TEXT," + // 3: author
                "\"TIME\" INTEGER," + // 4: time
                "\"CONTENT\" TEXT);"); // 5: content
        // Add Indexes
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_ARTICLE_ARTICLE_ID ON ARTICLE" +
                " (\"ARTICLE_ID\");");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ARTICLE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Article entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long articleId = entity.getArticleId();
        if (articleId != null) {
            stmt.bindLong(2, articleId);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(4, author);
        }
 
        Timestamp time = entity.getTime();
        if (time != null) {
            stmt.bindLong(5, timeConverter.convertToDatabaseValue(time));
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(6, content);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    protected Article readEntity(Cursor cursor, int offset) {
        Article entity = new Article( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // articleId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // title
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // author
            cursor.isNull(offset + 4) ? null : timeConverter.convertToEntityProperty(Timestamp.class, cursor.getLong(offset + 4)), // time
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // content
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    protected void readEntity(Cursor cursor, Article entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setArticleId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAuthor(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTime(cursor.isNull(offset + 4) ? null : timeConverter.convertToEntityProperty(Timestamp.class, cursor.getLong(offset + 4)));
        entity.setContent(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
    }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Article entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Article entity) {
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
    protected void notifyInsert(Article entity) {
        Long key = getKey(entity);
        if (key != null) {
            notifyExtraOb(key);

            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().insert(
                        ContentUris.withAppendedId(CONTENT_URI, key), null);
        }
    }

    @Override
    protected void notifyInsert(Iterable<Article> entities) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        for (Article entity : entities) {
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
    protected void notifyUpdate(Article entity) {
        Long key = getKey(entity);
        if (key != null) {
            notifyExtraOb(key);

            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().update(
                        ContentUris.withAppendedId(CONTENT_URI, key), null, null, null);
        }
    }

    @Override
    protected void notifyUpdate(Iterable<Article> entities) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        for (Article entity : entities) {
            Long key = getKey(entity);
            if (key != null) {
                notifyExtraOb(key);

                ops.add(ContentProviderOperation.newUpdate(
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
    protected void notifyDelete(Article entity) {
        Long key = getKey(entity);
        if (key != null) {
            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().delete(
                        ContentUris.withAppendedId(CONTENT_URI, key), null, null);
        }
    }

    @Override
    protected void notifyDelete(Iterable<Article> entities) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        for (Article entity : entities) {
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

    private Map<Long, WeakHashMap<IModelObservable<Article>, Boolean>> extraObMap = new HashMap<>();

    /**
     * register entity to extraObMap
     */
    public void registerExtraOb(IModelObservable entity) {
        if (entity == null) return;
        if (!extraObMap.containsKey(entity.getModelKey())) {
            extraObMap.put(entity.getModelKey(), new WeakHashMap<>());
        }
        WeakHashMap<IModelObservable<Article>, Boolean> map = extraObMap.get(entity.getModelKey());
        if (!map.containsKey(entity)) {
            map.put(entity, true);
        }
    }

    private void notifyExtraOb(Long key) {
        if (extraObMap.containsKey(key)) {
            Article newEntity = load(key);
            for (IModelObservable<Article> entity : extraObMap.get(key).keySet()) {
                if (entity != null)
                    entity.refresh(newEntity);
            }
        }
    }

}

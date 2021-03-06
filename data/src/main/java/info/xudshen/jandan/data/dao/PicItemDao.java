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

import info.xudshen.jandan.domain.model.PicItem;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PIC_ITEM".
*/
public class PicItemDao extends DDAbstractDao<PicItem, Long> {

    public static final String TABLENAME = "PIC_ITEM";

    /**
     * Properties of entity PicItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property PicId = new Property(1, Long.class, "picId", false, "PIC_ID");
        public final static Property Author = new Property(2, String.class, "author", false, "AUTHOR");
        public final static Property AuthorEmail = new Property(3, String.class, "authorEmail", false, "AUTHOR_EMAIL");
        public final static Property AuthorUrl = new Property(4, String.class, "authorUrl", false, "AUTHOR_URL");
        public final static Property Date = new Property(5, Long.class, "date", false, "DATE");
        public final static Property VotePositive = new Property(6, Long.class, "votePositive", false, "VOTE_POSITIVE");
        public final static Property VoteNegative = new Property(7, Long.class, "voteNegative", false, "VOTE_NEGATIVE");
        public final static Property CommentCount = new Property(8, Long.class, "commentCount", false, "COMMENT_COUNT");
        public final static Property ThreadId = new Property(9, String.class, "threadId", false, "THREAD_ID");
        public final static Property Content = new Property(10, String.class, "content", false, "CONTENT");
        public final static Property TextContent = new Property(11, String.class, "textContent", false, "TEXT_CONTENT");
        public final static Property Pics = new Property(12, String.class, "pics", false, "PICS");
        public final static Property PicFirst = new Property(13, String.class, "picFirst", false, "PIC_FIRST");
        public final static Property PicCount = new Property(14, Long.class, "picCount", false, "PIC_COUNT");
        public final static Property HasGif = new Property(15, Boolean.class, "hasGif", false, "HAS_GIF");
    }

    private final TimestampPropertyConverter dateConverter = new TimestampPropertyConverter();

    public PicItemDao(DaoConfig config) {
        super(config);
    }
    
    public PicItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"PIC_ITEM\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"PIC_ID\" INTEGER," + // 1: picId
                "\"AUTHOR\" TEXT," + // 2: author
                "\"AUTHOR_EMAIL\" TEXT," + // 3: authorEmail
                "\"AUTHOR_URL\" TEXT," + // 4: authorUrl
                "\"DATE\" INTEGER," + // 5: date
                "\"VOTE_POSITIVE\" INTEGER," + // 6: votePositive
                "\"VOTE_NEGATIVE\" INTEGER," + // 7: voteNegative
                "\"COMMENT_COUNT\" INTEGER," + // 8: commentCount
                "\"THREAD_ID\" TEXT," + // 9: threadId
                "\"CONTENT\" TEXT," + // 10: content
                "\"TEXT_CONTENT\" TEXT," + // 11: textContent
                "\"PICS\" TEXT," + // 12: pics
                "\"PIC_FIRST\" TEXT," + // 13: picFirst
                "\"PIC_COUNT\" INTEGER," + // 14: picCount
                "\"HAS_GIF\" INTEGER);"); // 15: hasGif
        // Add Indexes
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_PIC_ITEM_PIC_ID ON PIC_ITEM" +
                " (\"PIC_ID\");");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PIC_ITEM\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, PicItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long picId = entity.getPicId();
        if (picId != null) {
            stmt.bindLong(2, picId);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(3, author);
        }
 
        String authorEmail = entity.getAuthorEmail();
        if (authorEmail != null) {
            stmt.bindString(4, authorEmail);
        }
 
        String authorUrl = entity.getAuthorUrl();
        if (authorUrl != null) {
            stmt.bindString(5, authorUrl);
        }
 
        Timestamp date = entity.getDate();
        if (date != null) {
            stmt.bindLong(6, dateConverter.convertToDatabaseValue(date));
        }
 
        Long votePositive = entity.getVotePositive();
        if (votePositive != null) {
            stmt.bindLong(7, votePositive);
        }
 
        Long voteNegative = entity.getVoteNegative();
        if (voteNegative != null) {
            stmt.bindLong(8, voteNegative);
        }
 
        Long commentCount = entity.getCommentCount();
        if (commentCount != null) {
            stmt.bindLong(9, commentCount);
        }
 
        String threadId = entity.getThreadId();
        if (threadId != null) {
            stmt.bindString(10, threadId);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(11, content);
        }
 
        String textContent = entity.getTextContent();
        if (textContent != null) {
            stmt.bindString(12, textContent);
        }
 
        String pics = entity.getPics();
        if (pics != null) {
            stmt.bindString(13, pics);
        }
 
        String picFirst = entity.getPicFirst();
        if (picFirst != null) {
            stmt.bindString(14, picFirst);
        }
 
        Long picCount = entity.getPicCount();
        if (picCount != null) {
            stmt.bindLong(15, picCount);
        }
 
        Boolean hasGif = entity.getHasGif();
        if (hasGif != null) {
            stmt.bindLong(16, hasGif ? 1L: 0L);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    protected PicItem readEntity(Cursor cursor, int offset) {
        PicItem entity = new PicItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // picId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // author
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // authorEmail
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // authorUrl
            cursor.isNull(offset + 5) ? null : dateConverter.convertToEntityProperty(Timestamp.class, cursor.getLong(offset + 5)), // date
            cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6), // votePositive
            cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7), // voteNegative
            cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8), // commentCount
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // threadId
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // content
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // textContent
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // pics
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // picFirst
            cursor.isNull(offset + 14) ? null : cursor.getLong(offset + 14), // picCount
            cursor.isNull(offset + 15) ? null : cursor.getShort(offset + 15) != 0 // hasGif
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    protected void readEntity(Cursor cursor, PicItem entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPicId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setAuthor(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAuthorEmail(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAuthorUrl(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDate(cursor.isNull(offset + 5) ? null : dateConverter.convertToEntityProperty(Timestamp.class, cursor.getLong(offset + 5)));
        entity.setVotePositive(cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6));
        entity.setVoteNegative(cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7));
        entity.setCommentCount(cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8));
        entity.setThreadId(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setContent(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setTextContent(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setPics(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setPicFirst(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setPicCount(cursor.isNull(offset + 14) ? null : cursor.getLong(offset + 14));
        entity.setHasGif(cursor.isNull(offset + 15) ? null : cursor.getShort(offset + 15) != 0);
    }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(PicItem entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(PicItem entity) {
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
    protected void notifyInsert(PicItem entity) {
        Long key = getKey(entity);
        if (key != null) {
            notifyExtraOb(key);

            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().insert(
                        ContentUris.withAppendedId(CONTENT_URI, key), null);
        }
    }

    @Override
    protected void notifyInsert(Iterable<PicItem> entities) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        for (PicItem entity : entities) {
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
    protected void notifyUpdate(PicItem entity) {
        Long key = getKey(entity);
        if (key != null) {
            notifyExtraOb(key);

            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().update(
                        ContentUris.withAppendedId(CONTENT_URI, key), null, null, null);
        }
    }

    @Override
    protected void notifyUpdate(Iterable<PicItem> entities) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        for (PicItem entity : entities) {
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
    protected void notifyDelete(PicItem entity) {
        Long key = getKey(entity);
        if (key != null) {
            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().delete(
                        ContentUris.withAppendedId(CONTENT_URI, key), null, null);
        }
    }

    @Override
    protected void notifyDelete(Iterable<PicItem> entities) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        for (PicItem entity : entities) {
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

    private Map<Long, WeakHashMap<IModelObservable<PicItem>, Boolean>> extraObMap = new HashMap<>();

    /**
     * register entity to extraObMap
     */
    public void registerExtraOb(IModelObservable entity) {
        if (entity == null) return;
        if (!extraObMap.containsKey(entity.getModelKey())) {
            extraObMap.put(entity.getModelKey(), new WeakHashMap<>());
        }
        WeakHashMap<IModelObservable<PicItem>, Boolean> map = extraObMap.get(entity.getModelKey());
        if (!map.containsKey(entity)) {
            map.put(entity, true);
        }
    }

    private void notifyExtraOb(Long key) {
        if (extraObMap.containsKey(key)) {
            PicItem newEntity = load(key);
            for (IModelObservable<PicItem> entity : extraObMap.get(key).keySet()) {
                if (entity != null)
                    entity.refresh(newEntity);
            }
        }
    }

    public PicItem loadEntity(Cursor cursor){
        return this.readEntity(cursor, 0);
    }

}

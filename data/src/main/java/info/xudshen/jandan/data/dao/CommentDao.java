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

import info.xudshen.jandan.domain.model.Comment;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COMMENT".
*/
public class CommentDao extends DDAbstractDao<Comment, Long> {

    public static final String TABLENAME = "COMMENT";

    /**
     * Properties of entity Comment.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property CommentId = new Property(1, Long.class, "commentId", false, "COMMENT_ID");
        public final static Property PostId = new Property(2, Long.class, "postId", false, "POST_ID");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property Url = new Property(4, String.class, "url", false, "URL");
        public final static Property Date = new Property(5, Long.class, "date", false, "DATE");
        public final static Property Content = new Property(6, String.class, "content", false, "CONTENT");
        public final static Property Parent = new Property(7, Long.class, "parent", false, "PARENT");
        public final static Property VotePositive = new Property(8, Long.class, "votePositive", false, "VOTE_POSITIVE");
        public final static Property VoteNegative = new Property(9, Long.class, "voteNegative", false, "VOTE_NEGATIVE");
        public final static Property Index = new Property(10, Long.class, "index", false, "INDEX");
        public final static Property CommentTo = new Property(11, Long.class, "commentTo", false, "COMMENT_TO");
    }

    private final TimestampPropertyConverter dateConverter = new TimestampPropertyConverter();

    public CommentDao(DaoConfig config) {
        super(config);
    }
    
    public CommentDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"COMMENT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"COMMENT_ID\" INTEGER," + // 1: commentId
                "\"POST_ID\" INTEGER," + // 2: postId
                "\"NAME\" TEXT," + // 3: name
                "\"URL\" TEXT," + // 4: url
                "\"DATE\" INTEGER," + // 5: date
                "\"CONTENT\" TEXT," + // 6: content
                "\"PARENT\" INTEGER," + // 7: parent
                "\"VOTE_POSITIVE\" INTEGER," + // 8: votePositive
                "\"VOTE_NEGATIVE\" INTEGER," + // 9: voteNegative
                "\"INDEX\" INTEGER," + // 10: index
                "\"COMMENT_TO\" INTEGER);"); // 11: commentTo
        // Add Indexes
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_COMMENT_COMMENT_ID_POST_ID ON COMMENT" +
                " (\"COMMENT_ID\",\"POST_ID\");");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COMMENT\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Comment entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long commentId = entity.getCommentId();
        if (commentId != null) {
            stmt.bindLong(2, commentId);
        }
 
        Long postId = entity.getPostId();
        if (postId != null) {
            stmt.bindLong(3, postId);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(5, url);
        }
 
        Timestamp date = entity.getDate();
        if (date != null) {
            stmt.bindLong(6, dateConverter.convertToDatabaseValue(date));
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(7, content);
        }
 
        Long parent = entity.getParent();
        if (parent != null) {
            stmt.bindLong(8, parent);
        }
 
        Long votePositive = entity.getVotePositive();
        if (votePositive != null) {
            stmt.bindLong(9, votePositive);
        }
 
        Long voteNegative = entity.getVoteNegative();
        if (voteNegative != null) {
            stmt.bindLong(10, voteNegative);
        }
 
        Long index = entity.getIndex();
        if (index != null) {
            stmt.bindLong(11, index);
        }
 
        Long commentTo = entity.getCommentTo();
        if (commentTo != null) {
            stmt.bindLong(12, commentTo);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    protected Comment readEntity(Cursor cursor, int offset) {
        Comment entity = new Comment( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // commentId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // postId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // url
            cursor.isNull(offset + 5) ? null : dateConverter.convertToEntityProperty(Timestamp.class, cursor.getLong(offset + 5)), // date
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // content
            cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7), // parent
            cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8), // votePositive
            cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9), // voteNegative
            cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10), // index
            cursor.isNull(offset + 11) ? null : cursor.getLong(offset + 11) // commentTo
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    protected void readEntity(Cursor cursor, Comment entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCommentId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setPostId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUrl(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDate(cursor.isNull(offset + 5) ? null : dateConverter.convertToEntityProperty(Timestamp.class, cursor.getLong(offset + 5)));
        entity.setContent(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setParent(cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7));
        entity.setVotePositive(cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8));
        entity.setVoteNegative(cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9));
        entity.setIndex(cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10));
        entity.setCommentTo(cursor.isNull(offset + 11) ? null : cursor.getLong(offset + 11));
    }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Comment entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Comment entity) {
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
    protected void notifyInsert(Comment entity) {
        Long key = getKey(entity);
        if (key != null) {
            notifyExtraOb(key);

            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().insert(
                        ContentUris.withAppendedId(CONTENT_URI, key), null);
        }
    }

    @Override
    protected void notifyInsert(Iterable<Comment> entities) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        for (Comment entity : entities) {
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
    protected void notifyUpdate(Comment entity) {
        Long key = getKey(entity);
        if (key != null) {
            notifyExtraOb(key);

            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().update(
                        ContentUris.withAppendedId(CONTENT_URI, key), null, null, null);
        }
    }

    @Override
    protected void notifyUpdate(Iterable<Comment> entities) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        for (Comment entity : entities) {
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
    protected void notifyDelete(Comment entity) {
        Long key = getKey(entity);
        if (key != null) {
            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().delete(
                        ContentUris.withAppendedId(CONTENT_URI, key), null, null);
        }
    }

    @Override
    protected void notifyDelete(Iterable<Comment> entities) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        for (Comment entity : entities) {
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

    private Map<Long, WeakHashMap<IModelObservable<Comment>, Boolean>> extraObMap = new HashMap<>();

    /**
     * register entity to extraObMap
     */
    public void registerExtraOb(IModelObservable entity) {
        if (entity == null) return;
        if (!extraObMap.containsKey(entity.getModelKey())) {
            extraObMap.put(entity.getModelKey(), new WeakHashMap<>());
        }
        WeakHashMap<IModelObservable<Comment>, Boolean> map = extraObMap.get(entity.getModelKey());
        if (!map.containsKey(entity)) {
            map.put(entity, true);
        }
    }

    private void notifyExtraOb(Long key) {
        if (extraObMap.containsKey(key)) {
            Comment newEntity = load(key);
            for (IModelObservable<Comment> entity : extraObMap.get(key).keySet()) {
                if (entity != null)
                    entity.refresh(newEntity);
            }
        }
    }

    public Comment loadEntity(Cursor cursor){
        return this.readEntity(cursor, 0);
    }

}

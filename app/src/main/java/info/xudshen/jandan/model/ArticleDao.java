package info.xudshen.jandan.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import info.xudshen.droiddata.daogenerator.converter.TimestampPropertyConverter;
import java.sql.Timestamp;

import info.xudshen.jandan.model.Article;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ARTICLE".
*/
public class ArticleDao extends AbstractDao<Article, Long> {

    public static final String TABLENAME = "ARTICLE";

    /**
     * Properties of entity Article.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property ArticleId = new Property(0, Long.class, "articleId", true, "ARTICLE_ID");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Author = new Property(2, String.class, "author", false, "AUTHOR");
        public final static Property Time = new Property(3, Long.class, "time", false, "TIME");
        public final static Property Content = new Property(4, String.class, "content", false, "CONTENT");
    };

    private final TimestampPropertyConverter timeConverter = new TimestampPropertyConverter();

    public ArticleDao(DaoConfig config) {
        super(config);
    }
    
    public ArticleDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ARTICLE\" (" + //
                "\"ARTICLE_ID\" INTEGER PRIMARY KEY ," + // 0: articleId
                "\"TITLE\" TEXT," + // 1: title
                "\"AUTHOR\" TEXT," + // 2: author
                "\"TIME\" INTEGER," + // 3: time
                "\"CONTENT\" TEXT);"); // 4: content
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
 
        Long articleId = entity.getArticleId();
        if (articleId != null) {
            stmt.bindLong(1, articleId);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(3, author);
        }
 
        Timestamp time = entity.getTime();
        if (time != null) {
            stmt.bindLong(4, timeConverter.convertToDatabaseValue(time));
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(5, content);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Article readEntity(Cursor cursor, int offset) {
        Article entity = new Article( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // articleId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // title
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // author
            cursor.isNull(offset + 3) ? null : timeConverter.convertToEntityProperty(Timestamp.class, cursor.getLong(offset + 3)), // time
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // content
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Article entity, int offset) {
        entity.setArticleId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAuthor(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTime(cursor.isNull(offset + 3) ? null : timeConverter.convertToEntityProperty(Timestamp.class, cursor.getLong(offset + 3)));
        entity.setContent(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Article entity, long rowId) {
        entity.setArticleId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Article entity) {
        if(entity != null) {
            return entity.getArticleId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}

package info.xudshen.jandan.data.dao;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import de.greenrobot.dao.DaoLog;

/* Copy this code snippet into your AndroidManifest.xml inside the
<application> element:

    <provider
            android:name="info.xudshen.jandan.data.dao.ModelContentProvider"
            android:authorities="info.xudshen.jandan.data.dao.provider"/>
    */

public class ModelContentProvider extends ContentProvider {

    public static final String AUTHORITY = "info.xudshen.jandan.data.dao.provider";

    public static final String META_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/" + MetaDao.TABLENAME;
    public static final String META_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/" + MetaDao.TABLENAME;
    public static final String POST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/" + PostDao.TABLENAME;
    public static final String POST_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/" + PostDao.TABLENAME;
    public static final String SIMPLE_POST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/" + SimplePostDao.TABLENAME;
    public static final String SIMPLE_POST_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/" + SimplePostDao.TABLENAME;
    public static final String AUTHOR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/" + AuthorDao.TABLENAME;
    public static final String AUTHOR_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/" + AuthorDao.TABLENAME;
    public static final String CATEGORY_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/" + CategoryDao.TABLENAME;
    public static final String CATEGORY_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/" + CategoryDao.TABLENAME;
    public static final String COMMENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/" + CommentDao.TABLENAME;
    public static final String COMMENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/" + CommentDao.TABLENAME;
    public static final String PIC_ITEM_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/" + PicItemDao.TABLENAME;
    public static final String PIC_ITEM_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/" + PicItemDao.TABLENAME;

    private static final int META_DIR = 0x0000;
    private static final int META_ID = 0x1000;
    private static final int POST_DIR = 0x0001;
    private static final int POST_ID = 0x1001;
    private static final int SIMPLE_POST_DIR = 0x0002;
    private static final int SIMPLE_POST_ID = 0x1002;
    private static final int AUTHOR_DIR = 0x0003;
    private static final int AUTHOR_ID = 0x1003;
    private static final int CATEGORY_DIR = 0x0004;
    private static final int CATEGORY_ID = 0x1004;
    private static final int COMMENT_DIR = 0x0005;
    private static final int COMMENT_ID = 0x1005;
    private static final int PIC_ITEM_DIR = 0x0006;
    private static final int PIC_ITEM_ID = 0x1006;

    private static final UriMatcher sURIMatcher;

    static {
        sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sURIMatcher.addURI(AUTHORITY, MetaDao.TABLENAME, META_DIR);
        sURIMatcher.addURI(AUTHORITY, MetaDao.TABLENAME + "/#", META_ID);
        sURIMatcher.addURI(AUTHORITY, PostDao.TABLENAME, POST_DIR);
        sURIMatcher.addURI(AUTHORITY, PostDao.TABLENAME + "/#", POST_ID);
        sURIMatcher.addURI(AUTHORITY, SimplePostDao.TABLENAME, SIMPLE_POST_DIR);
        sURIMatcher.addURI(AUTHORITY, SimplePostDao.TABLENAME + "/#", SIMPLE_POST_ID);
        sURIMatcher.addURI(AUTHORITY, AuthorDao.TABLENAME, AUTHOR_DIR);
        sURIMatcher.addURI(AUTHORITY, AuthorDao.TABLENAME + "/#", AUTHOR_ID);
        sURIMatcher.addURI(AUTHORITY, CategoryDao.TABLENAME, CATEGORY_DIR);
        sURIMatcher.addURI(AUTHORITY, CategoryDao.TABLENAME + "/#", CATEGORY_ID);
        sURIMatcher.addURI(AUTHORITY, CommentDao.TABLENAME, COMMENT_DIR);
        sURIMatcher.addURI(AUTHORITY, CommentDao.TABLENAME + "/#", COMMENT_ID);
        sURIMatcher.addURI(AUTHORITY, PicItemDao.TABLENAME, PIC_ITEM_DIR);
        sURIMatcher.addURI(AUTHORITY, PicItemDao.TABLENAME + "/#", PIC_ITEM_ID);
    }

    /**
     * This must be set from outside, it's recommended to do this inside your Application object.
     * Subject to change (static isn't nice).
     */
    public static DaoSession daoSession;

    @Override
    public boolean onCreate() {
        // if(daoSession == null) {
        // throw new IllegalStateException("DaoSession must be set before content provider is created");
        // }
        DaoLog.d("Content Provider started");
        return true;
    }

    protected SQLiteDatabase getDatabase() {
        if (daoSession == null) {
            throw new IllegalStateException("DaoSession must be set during content provider is active");
        }
        return daoSession.getDatabase();
    }

    /**
     * only accept uri with id
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case META_ID:
            case POST_ID:
            case SIMPLE_POST_ID:
            case AUTHOR_ID:
            case CATEGORY_ID:
            case COMMENT_ID:
            case PIC_ITEM_ID:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        if (uriType == -1) {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return 1;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case META_ID:
            case POST_ID:
            case SIMPLE_POST_ID:
            case AUTHOR_ID:
            case CATEGORY_ID:
            case COMMENT_ID:
            case PIC_ITEM_ID:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return 1;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case META_DIR:
                queryBuilder.setTables(MetaDao.TABLENAME);
                break;
            case META_ID:
                queryBuilder.setTables(MetaDao.TABLENAME);
                queryBuilder.appendWhere(MetaDao.Properties.Id.columnName + "="
                        + uri.getLastPathSegment());
                break;
            case POST_DIR:
                queryBuilder.setTables(PostDao.TABLENAME);
                break;
            case POST_ID:
                queryBuilder.setTables(PostDao.TABLENAME);
                queryBuilder.appendWhere(PostDao.Properties.Id.columnName + "="
                        + uri.getLastPathSegment());
                break;
            case SIMPLE_POST_DIR:
                queryBuilder.setTables(SimplePostDao.TABLENAME);
                break;
            case SIMPLE_POST_ID:
                queryBuilder.setTables(SimplePostDao.TABLENAME);
                queryBuilder.appendWhere(SimplePostDao.Properties.Id.columnName + "="
                        + uri.getLastPathSegment());
                break;
            case AUTHOR_DIR:
                queryBuilder.setTables(AuthorDao.TABLENAME);
                break;
            case AUTHOR_ID:
                queryBuilder.setTables(AuthorDao.TABLENAME);
                queryBuilder.appendWhere(AuthorDao.Properties.Id.columnName + "="
                        + uri.getLastPathSegment());
                break;
            case CATEGORY_DIR:
                queryBuilder.setTables(CategoryDao.TABLENAME);
                break;
            case CATEGORY_ID:
                queryBuilder.setTables(CategoryDao.TABLENAME);
                queryBuilder.appendWhere(CategoryDao.Properties.Id.columnName + "="
                        + uri.getLastPathSegment());
                break;
            case COMMENT_DIR:
                queryBuilder.setTables(CommentDao.TABLENAME);
                break;
            case COMMENT_ID:
                queryBuilder.setTables(CommentDao.TABLENAME);
                queryBuilder.appendWhere(CommentDao.Properties.Id.columnName + "="
                        + uri.getLastPathSegment());
                break;
            case PIC_ITEM_DIR:
                queryBuilder.setTables(PicItemDao.TABLENAME);
                break;
            case PIC_ITEM_ID:
                queryBuilder.setTables(PicItemDao.TABLENAME);
                queryBuilder.appendWhere(PicItemDao.Properties.Id.columnName + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = getDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public final String getType(Uri uri) {
        switch (sURIMatcher.match(uri)) {
            case META_DIR:
                return META_TYPE;
            case META_ID:
                return META_ITEM_TYPE;
            case POST_DIR:
                return POST_TYPE;
            case POST_ID:
                return POST_ITEM_TYPE;
            case SIMPLE_POST_DIR:
                return SIMPLE_POST_TYPE;
            case SIMPLE_POST_ID:
                return SIMPLE_POST_ITEM_TYPE;
            case AUTHOR_DIR:
                return AUTHOR_TYPE;
            case AUTHOR_ID:
                return AUTHOR_ITEM_TYPE;
            case CATEGORY_DIR:
                return CATEGORY_TYPE;
            case CATEGORY_ID:
                return CATEGORY_ITEM_TYPE;
            case COMMENT_DIR:
                return COMMENT_TYPE;
            case COMMENT_ID:
                return COMMENT_ITEM_TYPE;
            case PIC_ITEM_DIR:
                return PIC_ITEM_TYPE;
            case PIC_ITEM_ID:
                return PIC_ITEM_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}
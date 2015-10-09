package info.xudshen.jandan.model;

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
            android:name="info.xudshen.jandan.model.ModelContentProvider"
            android:authorities="info.xudshen.jandan.model.provider"/>
    */

public class ModelContentProvider extends ContentProvider {

    public static final String AUTHORITY = "info.xudshen.jandan.model.provider";

    public static final String ARTICLE_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/" + ArticleDao.TABLENAME;
    public static final String ARTICLE_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/" + ArticleDao.TABLENAME;
    public static final String JOKE_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/" + JokeDao.TABLENAME;
    public static final String JOKE_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/" + JokeDao.TABLENAME;

    private static final int ARTICLE_DIR = 0x0000;
    private static final int ARTICLE_ID = 0x1000;
    private static final int JOKE_DIR = 0x0001;
    private static final int JOKE_ID = 0x1001;

    private static final UriMatcher sURIMatcher;

    static {
        sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sURIMatcher.addURI(AUTHORITY, ArticleDao.TABLENAME, ARTICLE_DIR);
        sURIMatcher.addURI(AUTHORITY, ArticleDao.TABLENAME + "/#", ARTICLE_ID);
        sURIMatcher.addURI(AUTHORITY, JokeDao.TABLENAME, JOKE_DIR);
        sURIMatcher.addURI(AUTHORITY, JokeDao.TABLENAME + "/#", JOKE_ID);
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
            case ARTICLE_ID:
            case JOKE_ID:
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
            case ARTICLE_ID:
            case JOKE_ID:
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
            case ARTICLE_DIR:
                queryBuilder.setTables(ArticleDao.TABLENAME);
                break;
            case ARTICLE_ID:
                queryBuilder.setTables(ArticleDao.TABLENAME);
                queryBuilder.appendWhere(ArticleDao.Properties.ArticleId.columnName + "="
                        + uri.getLastPathSegment());
                break;
            case JOKE_DIR:
                queryBuilder.setTables(JokeDao.TABLENAME);
                break;
            case JOKE_ID:
                queryBuilder.setTables(JokeDao.TABLENAME);
                queryBuilder.appendWhere(JokeDao.Properties.JokeId.columnName + "="
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
            case ARTICLE_DIR:
                return ARTICLE_TYPE;
            case ARTICLE_ID:
                return ARTICLE_ITEM_TYPE;
            case JOKE_DIR:
                return JOKE_TYPE;
            case JOKE_ID:
                return JOKE_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}
package ${contentProvider.javaPackage};

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
            android:name="${contentProvider.javaPackage}.${contentProvider.className}"
            android:authorities="${contentProvider.authority}"/>
    */

public class ${contentProvider.className} extends ContentProvider {

    public static final String AUTHORITY = "${contentProvider.authority}";

<#list contentProvider.entities as entity>
    public static final String ${entity.tableName}_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/" + ${entity.classNameDao}.TABLENAME;
    public static final String ${entity.tableName}_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/" + ${entity.classNameDao}.TABLENAME;
</#list>

<#list contentProvider.entities as entity>
    private static final int ${entity.tableName}_DIR = 0x000${entity_index};
    private static final int ${entity.tableName}_ID = 0x100${entity_index};
</#list>

    private static final UriMatcher sURIMatcher;

    static {
        sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
<#list contentProvider.entities as entity>
        sURIMatcher.addURI(AUTHORITY, ${entity.classNameDao}.TABLENAME, ${entity.tableName}_DIR);
        sURIMatcher.addURI(AUTHORITY, ${entity.classNameDao}.TABLENAME + "/#", ${entity.tableName}_ID);
</#list>
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
<#list contentProvider.entities as entity>
            case ${entity.tableName}_ID:
</#list>
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
<#list contentProvider.entities as entity>
            case ${entity.tableName}_ID:
</#list>
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
<#list contentProvider.entities as entity>
            case ${entity.tableName}_DIR:
                queryBuilder.setTables(${entity.classNameDao}.TABLENAME);
                break;
            case ${entity.tableName}_ID:
                queryBuilder.setTables(${entity.classNameDao}.TABLENAME);
                queryBuilder.appendWhere(${entity.classNameDao}.Properties.${entity.pkProperty.propertyName?cap_first}.columnName + "="
                        + uri.getLastPathSegment());
                break;
</#list>
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
<#list contentProvider.entities as entity>
            case ${entity.tableName}_DIR:
                return ${entity.tableName}_TYPE;
            case ${entity.tableName}_ID:
                return ${entity.tableName}_ITEM_TYPE;
</#list>
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}
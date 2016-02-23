package info.xudshen.jandan.data.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

import info.xudshen.jandan.data.dao.MetaDao;
import info.xudshen.jandan.data.dao.PostDao;
import info.xudshen.jandan.data.dao.SimplePostDao;
import info.xudshen.jandan.data.dao.AuthorDao;
import info.xudshen.jandan.data.dao.CategoryDao;
import info.xudshen.jandan.data.dao.CommentDao;
import info.xudshen.jandan.data.dao.ReadLaterItemDao;
import info.xudshen.jandan.data.dao.PicItemDao;
import info.xudshen.jandan.data.dao.DuoshuoCommentDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 1): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        MetaDao.createTable(db, ifNotExists);
        PostDao.createTable(db, ifNotExists);
        SimplePostDao.createTable(db, ifNotExists);
        AuthorDao.createTable(db, ifNotExists);
        CategoryDao.createTable(db, ifNotExists);
        CommentDao.createTable(db, ifNotExists);
        ReadLaterItemDao.createTable(db, ifNotExists);
        PicItemDao.createTable(db, ifNotExists);
        DuoshuoCommentDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        MetaDao.dropTable(db, ifExists);
        PostDao.dropTable(db, ifExists);
        SimplePostDao.dropTable(db, ifExists);
        AuthorDao.dropTable(db, ifExists);
        CategoryDao.dropTable(db, ifExists);
        CommentDao.dropTable(db, ifExists);
        ReadLaterItemDao.dropTable(db, ifExists);
        PicItemDao.dropTable(db, ifExists);
        DuoshuoCommentDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(MetaDao.class);
        registerDaoClass(PostDao.class);
        registerDaoClass(SimplePostDao.class);
        registerDaoClass(AuthorDao.class);
        registerDaoClass(CategoryDao.class);
        registerDaoClass(CommentDao.class);
        registerDaoClass(ReadLaterItemDao.class);
        registerDaoClass(PicItemDao.class);
        registerDaoClass(DuoshuoCommentDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}

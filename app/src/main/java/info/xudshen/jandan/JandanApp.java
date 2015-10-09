package info.xudshen.jandan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import info.xudshen.jandan.model.DaoMaster;
import info.xudshen.jandan.model.DaoSession;
import info.xudshen.jandan.model.ModelContentProvider;

/**
 * Created by xudshen on 15/9/24.
 */
public class JandanApp extends MultiDexApplication {
    public static DaoSession daoSession;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SQLiteOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "default", null);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        daoSession = (new DaoMaster(db)).newSession();
        ModelContentProvider.daoSession = daoSession;
    }
}

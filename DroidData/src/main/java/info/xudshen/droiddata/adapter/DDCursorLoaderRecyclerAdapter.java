package info.xudshen.droiddata.adapter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;

/**
 * Created by xudshen on 15/10/8.
 * <p>
 * This class wraps the CursorLoader part
 */
public abstract class DDCursorLoaderRecyclerAdapter<VH extends RecyclerView.ViewHolder> extends DDCursorRecyclerAdapter<VH> implements LoaderManager.LoaderCallbacks<Cursor> {
    Uri mUri;
    String[] mProjection;
    String mSelection;
    String[] mSelectionArgs;
    String mSortOrder;

    public DDCursorLoaderRecyclerAdapter(Context context, Uri uri, String[] projection, String selection,
                                         String[] selectionArgs, String sortOrder) {
        super(context, null, FLAG_REGISTER_CONTENT_OBSERVER);
        this.mUri = uri;
        this.mProjection = projection;
        this.mSelection = selection;
        this.mSelectionArgs = selectionArgs;
        this.mSortOrder = sortOrder;
    }

    //<editor-fold desc="Loader">
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(mContext, mUri, mProjection, mSelection, mSelectionArgs, mSortOrder);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        this.swapCursor(null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        this.swapCursor(data);
    }
    //</editor-fold>
}

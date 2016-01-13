package info.xudshen.jandan.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.xudshen.droiddata.adapter.impl.DDViewBindingCursorLoaderAdapter;

/**
 * Created by xudshen on 16/1/13.
 */
public class HeaderVBCLAdapter extends DDViewBindingCursorLoaderAdapter {
    static final int TYPE_PLACEHOLDER = Integer.MIN_VALUE;

    public HeaderVBCLAdapter(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public ViewHolder createViewHolder(LayoutInflater inflater, int viewType, ViewGroup parent) {
        switch (viewType) {
            case TYPE_PLACEHOLDER: {
                View view = inflater.inflate(
                        com.github.florent37.materialviewpager.R.layout.material_view_pager_placeholder,
                        parent, false);
                return new ViewHolder(view);
            }
            default:
                return super.createViewHolder(inflater, viewType, parent);
        }
    }

    /**
     * we must override this because the position is {position + 1} now
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_PLACEHOLDER) return;

        if (!mDataValid) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (!mCursor.moveToPosition(position - 1)) {
            throw new IllegalStateException("couldn't move cursor to position " + (position - 1));
        }
        bindViewHolder(holder, mCursor);
    }

    @Override
    public int getItemViewType(int position) {
        return position < 1 ? TYPE_PLACEHOLDER : super.getItemViewType(position - 1);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    @Override
    public long getItemId(int position) {
        return position < 1 ? -1 : super.getItemId(position - 1);
    }

    public static class Builder {
        private HeaderVBCLAdapter obj;

        public Builder() {
        }

        public Builder cursorLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            this.obj = new HeaderVBCLAdapter(context, uri, projection, selection, selectionArgs, sortOrder);
            return this;
        }

        public Builder onItemClick(OnItemClickListener itemClickListener) {
            this.obj.setItemClickListener(itemClickListener);
            return this;
        }

        public Builder layoutSelector(ItemBindingLayoutSelector layoutSelector) {
            this.obj.setLayoutSelector(layoutSelector);
            return this;
        }

        public Builder bindingVariableAction(ItemBindingVariableAction bindingVariableAction) {
            this.obj.setBindingVariableAction(bindingVariableAction);
            return this;
        }

        public HeaderVBCLAdapter build() {
            return this.obj;
        }
    }
}

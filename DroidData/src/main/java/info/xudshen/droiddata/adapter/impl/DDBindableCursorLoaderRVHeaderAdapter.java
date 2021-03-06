package info.xudshen.droiddata.adapter.impl;

import android.content.Context;
import android.database.Cursor;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import info.xudshen.droiddata.adapter.IBindableViewHolder;
import info.xudshen.droiddata.adapter.UserActionRegistry;

/**
 * Created by xudshen on 16/1/13.
 */
public class DDBindableCursorLoaderRVHeaderAdapter<VH extends RecyclerView.ViewHolder>
        extends DDBindableCursorLoaderRVAdapter<VH> {
    static final int TYPE_PLACEHOLDER = Integer.MIN_VALUE;
    static final int NO_ITEM_ID = -1;
    private int mPlaceholderSize = 1;

    protected HeaderViewHolderCreator<VH> headerViewHolderCreator;
    protected HeaderViewDataBindingVariableAction headerViewDataBindingVariableAction;

    public DDBindableCursorLoaderRVHeaderAdapter(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public VH createViewHolder(LayoutInflater inflater, int viewType, ViewGroup parent) {
        switch (viewType) {
            case TYPE_PLACEHOLDER: {
                if (headerViewHolderCreator == null) {
                    throw new IllegalArgumentException("headerViewHolderCreator should not be null");
                }
                return headerViewHolderCreator.createHeaderViewHolder(inflater, viewType, parent);
            }
            default: {
                if (itemViewHolderCreator == null) {
                    throw new IllegalArgumentException("itemViewHolderCreator should not be null");
                }
                VH viewHolder = itemViewHolderCreator.createItemViewHolder(inflater, viewType, parent);
                if (!IBindableViewHolder.class.isAssignableFrom(viewHolder.getClass())) {
                    throw new IllegalArgumentException(viewHolder.getClass().getSimpleName()
                            + " should implement IBindableViewHolder");
                }
                ((IBindableViewHolder) viewHolder).registerOnItemClickListener(onItemClickListener, mPlaceholderSize);
                ((IBindableViewHolder) viewHolder).registerOnItemSubViewUserActionListener(userActionRegistries, mPlaceholderSize);
                return viewHolder;
            }
        }
    }

    /**
     * we must override this (not {@link #bindViewHolder(RecyclerView.ViewHolder, Cursor)})
     * because the position is {position + mPlaceholderSize} now
     */
    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (getItemViewType(position) == TYPE_PLACEHOLDER) {
            if (headerViewDataBindingVariableAction != null) {
                if (!IBindableViewHolder.class.isAssignableFrom(holder.getClass())) {
                    throw new IllegalArgumentException(holder.getClass().getSimpleName()
                            + " should implement IBindableViewHolder");
                }
                ViewDataBinding binding = ((IBindableViewHolder) holder).getViewDataBinding();
                headerViewDataBindingVariableAction.doViewDataBindingVariable(binding);
                binding.executePendingBindings();
            }
            return;
        }

        if (!mDataValid) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (!mCursor.moveToPosition(position - mPlaceholderSize)) {
            throw new IllegalStateException("couldn't move cursor to position " + (position - mPlaceholderSize));
        }
        bindViewHolder(holder, mCursor);
    }

    @Override
    public int getItemViewType(int position) {
        return position < mPlaceholderSize ? TYPE_PLACEHOLDER : super.getItemViewType(position - mPlaceholderSize);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + mPlaceholderSize;
    }

    @Override
    public long getItemId(int position) {
        return position < mPlaceholderSize ? NO_ITEM_ID : super.getItemId(position - mPlaceholderSize);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        headerViewHolderCreator = null;
        headerViewDataBindingVariableAction = null;
        super.onDetachedFromRecyclerView(recyclerView);
    }

    //<editor-fold desc="Getter && Setter">
    public void setHeaderViewHolderCreator(HeaderViewHolderCreator<VH> headerViewHolderCreator) {
        this.headerViewHolderCreator = headerViewHolderCreator;
    }

    public void setHeaderViewDataBindingVariableAction(HeaderViewDataBindingVariableAction headerViewDataBindingVariableAction) {
        this.headerViewDataBindingVariableAction = headerViewDataBindingVariableAction;
    }
    //</editor-fold>

    //<editor-fold desc="Interface">
    public interface HeaderViewHolderCreator<VH extends RecyclerView.ViewHolder> {
        /**
         * create view holder, bind view listener
         * if you create lots of listener,
         * try {@link info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVHeaderAdapter.Builder#onItemSubViewClickListener(int, info.xudshen.droiddata.adapter.UserActionRegistry.OnSubviewClickListener)}
         *
         * @param viewType always {@link #TYPE_PLACEHOLDER}
         */
        VH createHeaderViewHolder(LayoutInflater inflater, int viewType, ViewGroup parent);
    }

    public interface HeaderViewDataBindingVariableAction {
        /**
         * bind data with view, may called multi times
         */
        void doViewDataBindingVariable(ViewDataBinding viewDataBinding);
    }
    //</editor-fold>

    public static class Builder<VH extends RecyclerView.ViewHolder> {
        private DDBindableCursorLoaderRVHeaderAdapter<VH> obj;

        public Builder() {
        }

        public Builder cursorLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            this.obj = new DDBindableCursorLoaderRVHeaderAdapter<VH>(context, uri, projection, selection, selectionArgs, sortOrder);
            return this;
        }

        public Builder headerViewHolderCreator(HeaderViewHolderCreator<VH> headerViewHolderCreator) {
            this.obj.setHeaderViewHolderCreator(headerViewHolderCreator);
            return this;
        }

        public Builder headerViewDataBindingVariableAction(HeaderViewDataBindingVariableAction headerViewDataBindingVariableAction) {
            this.obj.setHeaderViewDataBindingVariableAction(headerViewDataBindingVariableAction);
            return this;
        }

        public Builder itemViewHolderCreator(ItemViewHolderCreator<VH> itemViewHolderCreator) {
            this.obj.setItemViewHolderCreator(itemViewHolderCreator);
            return this;
        }

        public Builder itemLayoutSelector(ItemLayoutSelector itemLayoutSelector) {
            this.obj.setItemLayoutSelector(itemLayoutSelector);
            return this;
        }

        public Builder itemViewDataBindingVariableAction(ItemViewDataBindingVariableAction itemViewDataBindingVariableAction) {
            this.obj.setItemViewDataBindingVariableAction(itemViewDataBindingVariableAction);
            return this;
        }

        public Builder onItemClickListener(UserActionRegistry.OnClickListener onItemClickListener) {
            this.obj.setOnItemClickListener(onItemClickListener);
            return this;
        }

        public Builder onItemSubViewClickListener(int viewId, UserActionRegistry.OnSubviewClickListener onSubviewClickListener) {
            this.obj.userActionRegistries.add(new UserActionRegistry(viewId, onSubviewClickListener));
            return this;
        }

        public DDBindableCursorLoaderRVHeaderAdapter build() {
            return this.obj;
        }
    }
}

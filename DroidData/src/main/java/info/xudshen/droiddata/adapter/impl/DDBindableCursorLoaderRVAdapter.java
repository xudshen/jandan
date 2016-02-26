package info.xudshen.droiddata.adapter.impl;

import android.content.Context;
import android.database.Cursor;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import info.xudshen.droiddata.adapter.DDCursorLoaderRVAdapter;
import info.xudshen.droiddata.adapter.IBindableViewHolder;
import info.xudshen.droiddata.adapter.UserActionRegistry;

/**
 * Created by xudshen on 15/10/10.
 */
public class DDBindableCursorLoaderRVAdapter<VH extends RecyclerView.ViewHolder> extends
        DDCursorLoaderRVAdapter<VH> {

    protected ItemViewHolderCreator<VH> itemViewHolderCreator;
    protected ItemLayoutSelector itemLayoutSelector;
    protected ItemViewDataBindingVariableAction itemViewDataBindingVariableAction;

    protected UserActionRegistry.OnClickListener onItemClickListener;
    protected List<UserActionRegistry> userActionRegistries = new ArrayList<>();

    public DDBindableCursorLoaderRVAdapter(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }

    /**
     * @param viewType judged by {@link #getItemViewType(int)}
     */
    @Override
    public VH createViewHolder(LayoutInflater inflater, int viewType, ViewGroup parent) {
        if (itemViewHolderCreator == null) {
            throw new IllegalArgumentException("itemViewHolderCreator should not be null");
        }
        VH viewHolder = itemViewHolderCreator.createItemViewHolder(inflater, viewType, parent);
//                new ViewHolder(DataBindingUtil.inflate(inflater, viewType, parent, false));
        if (!IBindableViewHolder.class.isAssignableFrom(viewHolder.getClass())) {
            throw new IllegalArgumentException(viewHolder.getClass().getSimpleName()
                    + " should implement IBindableViewHolder");
        }
        ((IBindableViewHolder) viewHolder).registerOnItemClickListener(onItemClickListener, 0);
        ((IBindableViewHolder) viewHolder).registerOnItemSubViewUserActionListener(userActionRegistries, 0);
        return viewHolder;
    }

    @Override
    public void bindViewHolder(VH viewHolder, Cursor cursor) {
        if (!IBindableViewHolder.class.isAssignableFrom(viewHolder.getClass())) {
            throw new IllegalArgumentException(viewHolder.getClass().getSimpleName()
                    + " should implement IBindableViewHolder");
        }
        ViewDataBinding binding = ((IBindableViewHolder) viewHolder).getViewDataBinding();
        if (itemViewDataBindingVariableAction == null) {
            throw new IllegalArgumentException("itemViewDataBindingVariableAction should not be null");
        }
        itemViewDataBindingVariableAction.doViewDataBindingVariable(binding, cursor);
        binding.executePendingBindings();
    }

    @Override
    public int getItemViewType(int position) {
        if (itemLayoutSelector == null) {
            throw new IllegalArgumentException("itemLayoutSelector should not be null");
        }
        if (!mDataValid) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        return itemLayoutSelector.getItemLayoutRes(position, mCursor);
    }

    //<editor-fold desc="Getter & Setter">
    public void setItemViewHolderCreator(ItemViewHolderCreator<VH> itemViewHolderCreator) {
        this.itemViewHolderCreator = itemViewHolderCreator;
    }

    public void setItemLayoutSelector(ItemLayoutSelector itemLayoutSelector) {
        this.itemLayoutSelector = itemLayoutSelector;
    }

    public void setItemViewDataBindingVariableAction(ItemViewDataBindingVariableAction itemViewDataBindingVariableAction) {
        this.itemViewDataBindingVariableAction = itemViewDataBindingVariableAction;
    }

    public void setOnItemClickListener(UserActionRegistry.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    //</editor-fold>

    //<editor-fold desc="interface">
    public interface ItemViewHolderCreator<VH extends RecyclerView.ViewHolder> {
        /**
         * create view holder, bind view listener
         * if you create lots of listener,
         * try {@link info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVAdapter.Builder#onItemSubViewClickListener(int, UserActionRegistry.OnClickListener)}
         *
         * @param viewType value from {@link #itemLayoutSelector}
         */
        VH createItemViewHolder(LayoutInflater inflater, int viewType, ViewGroup parent);
    }

    public interface ItemLayoutSelector {
        int getItemLayoutRes(int position, Cursor cursor);
    }

    public interface ItemViewDataBindingVariableAction {
        /**
         * bind data with view, may called multi times
         */
        void doViewDataBindingVariable(ViewDataBinding viewDataBinding, Cursor cursor);
    }
    //</editor-fold>

    public static class Builder<VH extends RecyclerView.ViewHolder> {
        private DDBindableCursorLoaderRVAdapter<VH> obj;

        public Builder() {
        }

        public Builder cursorLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            this.obj = new DDBindableCursorLoaderRVAdapter<VH>(context, uri, projection, selection, selectionArgs, sortOrder);
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

        public Builder onItemSubViewClickListener(int viewId, UserActionRegistry.OnClickListener onClickListener) {
            this.obj.userActionRegistries.add(new UserActionRegistry(viewId, onClickListener));
            return this;
        }

        public DDBindableCursorLoaderRVAdapter build() {
            return this.obj;
        }
    }
}

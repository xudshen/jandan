package info.xudshen.droiddata.adapter.impl;

import android.content.Context;
import android.database.Cursor;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.xudshen.droiddata.adapter.DDCursorLoaderRVAdapter;
import info.xudshen.droiddata.adapter.IBindableViewHolder;

/**
 * Created by xudshen on 15/10/10.
 */
public class DDBindableCursorLoaderRVAdapter<VH extends RecyclerView.ViewHolder> extends
        DDCursorLoaderRVAdapter<VH> {

    protected BindableViewHolderCreator<VH> bindableViewHolderCreator;
    protected ItemLayoutSelector itemLayoutSelector;
    protected ItemViewDataBindingVariableAction itemViewDataBindingVariableAction;

    protected OnItemClickListener onItemClickListener;

    public DDBindableCursorLoaderRVAdapter(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }

    /**
     * @param viewType judged by {@link #getItemViewType(int)}
     */
    @Override
    public VH createViewHolder(LayoutInflater inflater, int viewType, ViewGroup parent) {
        if (bindableViewHolderCreator == null) {
            throw new IllegalArgumentException("bindableViewHolderCreator should not be null");
        }
        VH viewHolder = bindableViewHolderCreator.createBindableViewHolder(inflater, viewType, parent);
//                new ViewHolder(DataBindingUtil.inflate(inflater, viewType, parent, false));
        if (!IBindableViewHolder.class.isAssignableFrom(viewHolder.getClass())) {
            throw new IllegalArgumentException(viewHolder.getClass().getSimpleName()
                    + " should implement IBindableViewHolder");
        }
        ((IBindableViewHolder) viewHolder).registerOnItemClickListener(onItemClickListener, 0);
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
        return itemLayoutSelector.getItemLayoutRes(position);
    }

    //<editor-fold desc="Getter & Setter">
    public BindableViewHolderCreator<VH> getBindableViewHolderCreator() {
        return bindableViewHolderCreator;
    }

    public void setBindableViewHolderCreator(BindableViewHolderCreator<VH> bindableViewHolderCreator) {
        this.bindableViewHolderCreator = bindableViewHolderCreator;
    }

    public ItemLayoutSelector getItemLayoutSelector() {
        return itemLayoutSelector;
    }

    public void setItemLayoutSelector(ItemLayoutSelector itemLayoutSelector) {
        this.itemLayoutSelector = itemLayoutSelector;
    }

    public ItemViewDataBindingVariableAction getItemViewDataBindingVariableAction() {
        return itemViewDataBindingVariableAction;
    }

    public void setItemViewDataBindingVariableAction(ItemViewDataBindingVariableAction itemViewDataBindingVariableAction) {
        this.itemViewDataBindingVariableAction = itemViewDataBindingVariableAction;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    //</editor-fold>

    //<editor-fold desc="interface">
    public interface BindableViewHolderCreator<VH extends RecyclerView.ViewHolder> {
        VH createBindableViewHolder(LayoutInflater inflater, int viewType, ViewGroup parent);
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public interface ItemLayoutSelector {
        int getItemLayoutRes(int position);
    }

    public interface ItemViewDataBindingVariableAction {
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

        public Builder bindableViewHolderCreator(BindableViewHolderCreator<VH> bindableViewHolderCreator) {
            this.obj.setBindableViewHolderCreator(bindableViewHolderCreator);
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

        public Builder onItemClickListener(OnItemClickListener onItemClickListener) {
            this.obj.setOnItemClickListener(onItemClickListener);
            return this;
        }

        public DDBindableCursorLoaderRVAdapter build() {
            return this.obj;
        }
    }
}

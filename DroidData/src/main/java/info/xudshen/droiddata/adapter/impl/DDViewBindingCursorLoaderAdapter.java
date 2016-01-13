package info.xudshen.droiddata.adapter.impl;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.xudshen.droiddata.adapter.DDCursorLoaderRecyclerAdapter;

/**
 * Created by xudshen on 15/10/10.
 */
public class DDViewBindingCursorLoaderAdapter extends
        DDCursorLoaderRecyclerAdapter<DDViewBindingCursorLoaderAdapter.ViewHolder> {

    protected OnItemClickListener itemClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewDataBinding binding;

        public ViewHolder(View view) {
            super(view);
        }

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void registerOnItemClickListener(final OnItemClickListener itemClickListener) {
            if (this.binding != null)
                this.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemClickListener != null) {
                            itemClickListener.onItemClick(binding.getRoot(), getLayoutPosition());
                        }
                    }
                });
        }
    }

    //<editor-fold desc="ItemBinding">
    protected ItemBindingLayoutSelector layoutSelector;
    protected ItemBindingVariableAction bindingVariableAction;

    public DDViewBindingCursorLoaderAdapter(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }
    //</editor-fold>

    /**
     * @param viewType judged by {@link #getItemViewType(int)}
     */
    @Override
    public ViewHolder createViewHolder(LayoutInflater inflater, int viewType, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder(DataBindingUtil.inflate(inflater, viewType, parent, false));
        viewHolder.registerOnItemClickListener(itemClickListener);
        return viewHolder;
    }

    @Override
    public void bindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        if (bindingVariableAction == null) {
            throw new IllegalArgumentException("bindingVariableAction should not be null");
        }
        bindingVariableAction.doBindingVariable(viewHolder.binding, cursor);
        viewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemViewType(int position) {
        if (layoutSelector == null) {
            throw new IllegalArgumentException("layoutSelector should not be null");
        }
        return layoutSelector.getItemBindingLayoutRes(position);
    }

    //<editor-fold desc="Getter & Setter">
    public OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ItemBindingLayoutSelector getLayoutSelector() {
        return layoutSelector;
    }

    public void setLayoutSelector(ItemBindingLayoutSelector layoutSelector) {
        this.layoutSelector = layoutSelector;
    }

    public ItemBindingVariableAction getBindingVariableAction() {
        return bindingVariableAction;
    }

    public void setBindingVariableAction(ItemBindingVariableAction bindingVariableAction) {
        this.bindingVariableAction = bindingVariableAction;
    }
    //</editor-fold>

    //<editor-fold desc="interface">
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public interface ItemBindingLayoutSelector {
        int getItemBindingLayoutRes(int position);
    }

    public interface OnCreateViewHolderInjector {
        RecyclerView.ViewHolder onCreateViewHolder(LayoutInflater inflater, int viewType, ViewGroup parent);
    }

    public interface ItemBindingVariableAction {
        void doBindingVariable(ViewDataBinding viewDataBinding, Cursor cursor);
    }
    //</editor-fold>

    public static class Builder {
        private DDViewBindingCursorLoaderAdapter obj;

        public Builder() {
        }

        public Builder cursorLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            this.obj = new DDViewBindingCursorLoaderAdapter(context, uri, projection, selection, selectionArgs, sortOrder);
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

        public DDViewBindingCursorLoaderAdapter build() {
            return this.obj;
        }
    }
}

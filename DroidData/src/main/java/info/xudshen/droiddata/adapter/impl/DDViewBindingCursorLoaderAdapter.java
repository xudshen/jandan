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

    private OnItemClickListener itemClickListener;

    public DDViewBindingCursorLoaderAdapter onItemClick(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        return this;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void registerOnItemClickListener(final OnItemClickListener itemClickListener) {
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
    private ItemBindingLayoutSelector layoutSelector;
    private ItemBindingVariableAction bindingVariableAction;

    public DDViewBindingCursorLoaderAdapter(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }

    public DDViewBindingCursorLoaderAdapter layoutSelector(ItemBindingLayoutSelector layoutSelector) {
        this.layoutSelector = layoutSelector;
        return this;
    }

    public DDViewBindingCursorLoaderAdapter bindingVariableAction(ItemBindingVariableAction bindingVariableAction) {
        this.bindingVariableAction = bindingVariableAction;
        return this;
    }
    //</editor-fold>

    /**
     * @param viewType judged by {@link #getItemViewType(int)}
     */
    @Override
    public ViewHolder onCreateViewHolder(LayoutInflater inflater, int viewType, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder(DataBindingUtil.inflate(inflater, viewType, parent, false));
        viewHolder.registerOnItemClickListener(itemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
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

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public interface ItemBindingLayoutSelector {
        int getItemBindingLayoutRes(int position);
    }

    public interface ItemBindingVariableAction {
        void doBindingVariable(ViewDataBinding viewDataBinding, Cursor cursor);
    }
}

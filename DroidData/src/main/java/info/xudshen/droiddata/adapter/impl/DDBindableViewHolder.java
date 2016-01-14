package info.xudshen.droiddata.adapter.impl;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import info.xudshen.droiddata.adapter.IBindableViewHolder;

/**
 * Created by xudshen on 16/1/14.
 */
public class DDBindableViewHolder extends RecyclerView.ViewHolder implements IBindableViewHolder {
    private ViewDataBinding binding;

    public DDBindableViewHolder(View itemView) {
        super(itemView);
    }

    public DDBindableViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @Override
    public ViewDataBinding getViewDataBinding() {
        return binding;
    }

    @Override
    public void registerOnItemClickListener(
            final DDBindableCursorLoaderRVAdapter.OnItemClickListener onItemClickListener,
            final int mPlaceholderSize) {
        if (itemView != null)
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(itemView, getLayoutPosition() - mPlaceholderSize);
                    }
                }
            });
    }
}
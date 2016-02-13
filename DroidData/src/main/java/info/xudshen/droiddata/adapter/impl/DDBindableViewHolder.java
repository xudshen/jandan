package info.xudshen.droiddata.adapter.impl;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import info.xudshen.droiddata.adapter.IBindableViewHolder;
import info.xudshen.droiddata.adapter.UserActionRegistry;

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
            final UserActionRegistry.OnClickListener onClickListener,
            final int mPlaceholderSize) {
        if (itemView != null)
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(itemView, getLayoutPosition() - mPlaceholderSize);
                    }
                }
            });
    }

    @Override
    public void registerOnItemSubViewUserActionListener(
            List<UserActionRegistry> userActionRegistries,
            final int mPlaceholderSize) {
        for (final UserActionRegistry userActionRegistry : userActionRegistries) {
            View view = itemView.findViewById(userActionRegistry.getViewId());
            if (view != null) {
                switch (userActionRegistry.getType()) {
                    case CLICK: {
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (userActionRegistry.getOnClickListener() != null) {
                                    userActionRegistry.getOnClickListener()
                                            .onClick(v, getLayoutPosition() - mPlaceholderSize);
                                }
                            }
                        });
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
        }
    }
}
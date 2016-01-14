package info.xudshen.droiddata.adapter;

import android.databinding.ViewDataBinding;

import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVAdapter;

/**
 * Created by xudshen on 16/1/14.
 */
public interface IBindableViewHolder {
    ViewDataBinding getViewDataBinding();

    void registerOnItemClickListener(DDBindableCursorLoaderRVAdapter.OnItemClickListener onItemClickListener,
                                     int mPlaceholderSize);
}

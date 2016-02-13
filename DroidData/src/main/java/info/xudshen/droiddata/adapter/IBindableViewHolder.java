package info.xudshen.droiddata.adapter;

import android.databinding.ViewDataBinding;

import java.util.List;

/**
 * Created by xudshen on 16/1/14.
 */
public interface IBindableViewHolder {
    ViewDataBinding getViewDataBinding();

    void registerOnItemClickListener(UserActionRegistry.OnClickListener onClickListener,
                                     int mPlaceholderSize);

    void registerOnItemSubViewUserActionListener(List<UserActionRegistry> userActionRegistries,
                                                 int mPlaceholderSize);
}

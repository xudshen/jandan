package info.xudshen.jandan.adapter;

import android.app.Activity;

import info.xudshen.droiddata.adapter.impl.DDViewBindingCursorLoaderAdapter;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.dao.PostDao;
import info.xudshen.jandan.domain.model.Post;

/**
 * Created by xudshen on 15/10/9.
 */
public class RecyclerViewAdapterFactory {
    public static DDViewBindingCursorLoaderAdapter getPostListAdapter(Activity activity) {
        return (new DDViewBindingCursorLoaderAdapter(
                activity, PostDao.CONTENT_URI, null, null, null, "post_id desc limit 20"))
                .layoutSelector(position -> R.layout.my_text_view)
                .bindingVariableAction((viewDataBinding, cursor) -> {
                    Post post = null;
                    viewDataBinding.setVariable(BR.item, post);
                });
    }
}

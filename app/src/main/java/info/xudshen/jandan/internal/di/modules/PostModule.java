package info.xudshen.jandan.internal.di.modules;

import android.app.Activity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import info.xudshen.droiddata.adapter.impl.DDViewBindingCursorLoaderAdapter;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.dao.PostDao;
import info.xudshen.jandan.domain.model.Post;
import info.xudshen.jandan.internal.di.PerActivity;

/**
 * Created by xudshen on 16/1/7.
 */
@Module
public class PostModule {
    private int postId = -1;

    public PostModule() {
    }

    @Provides
    @PerActivity
    @Named("postListAdapter")
    DDViewBindingCursorLoaderAdapter providePostListAdapter(Activity activity, PostDao postDao) {
        return (new DDViewBindingCursorLoaderAdapter(
                activity, PostDao.CONTENT_URI, null, null, null, "post_id desc"))
                .layoutSelector(position -> R.layout.my_text_view)
                .bindingVariableAction((viewDataBinding, cursor) -> {
                    Post post = postDao.loadEntity(cursor);
                    viewDataBinding.setVariable(BR.item, post);
                });
    }
}

package info.xudshen.jandan.internal.di.modules;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.PopupMenu;
import android.widget.ImageButton;
import android.widget.Toast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVHeaderAdapter;
import info.xudshen.droiddata.adapter.impl.DDBindableViewHolder;
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
    private static final Logger logger = LoggerFactory.getLogger(PostModule.class);
    private int postId = -1;

    public PostModule() {
    }

    @Provides
    @PerActivity
    @Named("postListAdapter")
    DDBindableCursorLoaderRVHeaderAdapter providePostListAdapter(Activity activity, PostDao postDao) {
        return new DDBindableCursorLoaderRVHeaderAdapter.Builder<DDBindableViewHolder>()
                .cursorLoader(activity, PostDao.CONTENT_URI, null, null, null, "post_id desc")
                .headerViewHolderCreator((inflater, viewType, parent) -> {
                    return new DDBindableViewHolder(inflater.inflate(
                            com.github.florent37.materialviewpager.R.layout.material_view_pager_placeholder,
                            parent, false));
                })
                .bindableViewHolderCreator(((inflater1, viewType1, parent1) -> {
                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater1, viewType1, parent1, false);
                    ImageButton button = (ImageButton) viewDataBinding.getRoot().findViewById(R.id.post_card_more_btn);
                    button.setOnClickListener(v -> {
                        logger.info("more clicked");
                        PopupMenu popupMenu = new PopupMenu(activity, button);
                        popupMenu.setOnMenuItemClickListener(item -> {
                            switch (item.getItemId()) {
                                case R.id.post_card_more_menu_favo:
                                    logger.info("Favo Clicked");
                                    return true;
                                case R.id.post_card_more_menu_later:
                                    logger.info("Later Clicked");
                                    return true;
                                default:
                                    return false;
                            }
                        });
                        popupMenu.inflate(R.menu.post_card_more_menu);
                        popupMenu.show();
                    });
                    return new DDBindableViewHolder(viewDataBinding);
                }))
                .itemLayoutSelector(position -> R.layout.post_card_view)
                .itemViewDataBindingVariableAction((viewDataBinding, cursor) -> {
                    Post post = postDao.loadEntity(cursor);
                    viewDataBinding.setVariable(BR.item, post);
                })
                .build();
    }
}

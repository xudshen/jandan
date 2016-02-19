package info.xudshen.jandan.internal.di.modules;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.PopupMenu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVHeaderAdapter;
import info.xudshen.droiddata.adapter.impl.DDBindableViewHolder;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.dao.SimplePostDao;
import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.interactor.GetPostComment;
import info.xudshen.jandan.domain.interactor.GetPostDetail;
import info.xudshen.jandan.domain.interactor.GetPostList;
import info.xudshen.jandan.domain.interactor.IterableUseCase;
import info.xudshen.jandan.domain.interactor.UseCase;
import info.xudshen.jandan.domain.model.SimplePost;
import info.xudshen.jandan.domain.repository.PostRepository;
import info.xudshen.jandan.internal.di.PerActivity;

/**
 * Created by xudshen on 16/1/7.
 */
@Module
public class PostModule {
    private static final Logger logger = LoggerFactory.getLogger(PostModule.class);

    public PostModule() {
    }

    @Provides
    @PerActivity
    @Named("postListAdapter")
    DDBindableCursorLoaderRVHeaderAdapter providePostListAdapter(Activity activity, SimplePostDao simplePostDao) {
        return new DDBindableCursorLoaderRVHeaderAdapter.Builder<DDBindableViewHolder>()
                .cursorLoader(activity, SimplePostDao.CONTENT_URI, null, null, null, "date desc")
                .headerViewHolderCreator((inflater, viewType, parent) -> {
                    return new DDBindableViewHolder(inflater.inflate(
                            com.github.florent37.materialviewpager.R.layout.material_view_pager_placeholder,
                            parent, false));
                })
                .itemViewHolderCreator(((inflater1, viewType1, parent1) -> {
                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater1, viewType1, parent1, false);
                    return new DDBindableViewHolder(viewDataBinding);
                }))
                .itemLayoutSelector(position -> R.layout.post_card_view)
                .itemViewDataBindingVariableAction((viewDataBinding, cursor) -> {
                    SimplePost simplePost = simplePostDao.loadEntity(cursor);
                    viewDataBinding.setVariable(BR.item, simplePost);
                })
                .onItemSubViewClickListener(R.id.post_card_more_btn, ((v, position) -> {
                    logger.info("{} more clicked", position);
                    PopupMenu popupMenu = new PopupMenu(activity, v);
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
                }))
                .build();
    }

    @Provides
    @PerActivity
    @Named("postDetail")
    UseCase provideGetPostDetailUseCase(PostRepository postRepository,
                                        ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetPostDetail(postRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("postList")
    IterableUseCase provideGetPostListUseCase(PostRepository postRepository,
                                              ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetPostList(postRepository, threadExecutor, postExecutionThread);
    }


    @Provides
    @PerActivity
    @Named("postComment")
    IterableUseCase provideGetPostCommentUseCase(PostRepository postRepository,
                                                 ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetPostComment(postRepository, threadExecutor, postExecutionThread);
    }
}

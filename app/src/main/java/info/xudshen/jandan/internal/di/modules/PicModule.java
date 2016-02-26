package info.xudshen.jandan.internal.di.modules;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVHeaderAdapter;
import info.xudshen.droiddata.adapter.impl.DDBindableViewHolder;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.dao.PicItemDao;
import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.interactor.GetDuoshuoCommentList;
import info.xudshen.jandan.domain.interactor.GetPicDetail;
import info.xudshen.jandan.domain.interactor.GetPicList;
import info.xudshen.jandan.domain.interactor.IterableUseCase;
import info.xudshen.jandan.domain.interactor.UseCase;
import info.xudshen.jandan.domain.model.PicItem;
import info.xudshen.jandan.domain.repository.CommentRepository;
import info.xudshen.jandan.domain.repository.PicRepository;
import info.xudshen.jandan.internal.di.PerActivity;

/**
 * Created by xudshen on 16/2/20.
 */
@Module
public class PicModule {
    private static final Logger logger = LoggerFactory.getLogger(PicModule.class);

    public PicModule() {
    }

    @Provides
    @PerActivity
    @Named("picListAdapter")
    DDBindableCursorLoaderRVHeaderAdapter providePostListAdapter(Activity activity, PicItemDao picItemDao) {
        return new DDBindableCursorLoaderRVHeaderAdapter.Builder<DDBindableViewHolder>()
                .cursorLoader(activity, PicItemDao.CONTENT_URI, null, null, null, PicItemDao.Properties.Date.columnName + " desc")
                .headerViewHolderCreator((inflater, viewType, parent) -> {
                    return new DDBindableViewHolder(inflater.inflate(
                            com.github.florent37.materialviewpager.R.layout.material_view_pager_placeholder,
                            parent, false));
                })
                .itemViewHolderCreator(((inflater1, viewType1, parent1) -> {
                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater1, viewType1, parent1, false);
                    return new DDBindableViewHolder(viewDataBinding);
                }))
                .itemLayoutSelector((position, cursor) -> R.layout.pic_card_view)
                .itemViewDataBindingVariableAction((viewDataBinding, cursor) -> {
                    PicItem picItem = picItemDao.loadEntity(cursor);
                    viewDataBinding.setVariable(BR.item, picItem);
                })
                .build();
    }

    @Provides
    @PerActivity
    @Named("picDetail")
    UseCase provideGetPicDetailUseCase(PicRepository picRepository,
                                       ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetPicDetail(picRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("picList")
    IterableUseCase provideGetPicListUseCase(PicRepository picRepository,
                                             ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetPicList(picRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("duoshuoCommentList")
    IterableUseCase provideGetDuoshuoCommentListUseCase(CommentRepository commentRepository,
                                                        ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetDuoshuoCommentList(commentRepository, threadExecutor, postExecutionThread);
    }
}

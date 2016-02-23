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
import info.xudshen.jandan.data.dao.JokeItemDao;
import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.interactor.GetDuoshuoCommentList;
import info.xudshen.jandan.domain.interactor.GetJokeDetail;
import info.xudshen.jandan.domain.interactor.GetJokeList;
import info.xudshen.jandan.domain.interactor.IterableUseCase;
import info.xudshen.jandan.domain.interactor.UseCase;
import info.xudshen.jandan.domain.model.JokeItem;
import info.xudshen.jandan.domain.repository.CommentRepository;
import info.xudshen.jandan.domain.repository.JokeRepository;
import info.xudshen.jandan.internal.di.PerActivity;

/**
 * Created by xudshen on 16/2/20.
 */
@Module
public class JokeModule {
    private static final Logger logger = LoggerFactory.getLogger(JokeModule.class);

    public JokeModule() {
    }

    @Provides
    @PerActivity
    @Named("jokeListAdapter")
    DDBindableCursorLoaderRVHeaderAdapter providePostListAdapter(Activity activity, JokeItemDao jokeItemDao) {
        return new DDBindableCursorLoaderRVHeaderAdapter.Builder<DDBindableViewHolder>()
                .cursorLoader(activity, JokeItemDao.CONTENT_URI, null, null, null, JokeItemDao.Properties.Date.columnName + " desc")
                .headerViewHolderCreator((inflater, viewType, parent) -> {
                    return new DDBindableViewHolder(inflater.inflate(
                            com.github.florent37.materialviewpager.R.layout.material_view_pager_placeholder,
                            parent, false));
                })
                .itemViewHolderCreator(((inflater1, viewType1, parent1) -> {
                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater1, viewType1, parent1, false);
                    return new DDBindableViewHolder(viewDataBinding);
                }))
                .itemLayoutSelector(position -> R.layout.joke_card_view)
                .itemViewDataBindingVariableAction((viewDataBinding, cursor) -> {
                    JokeItem jokeItem = jokeItemDao.loadEntity(cursor);
                    viewDataBinding.setVariable(BR.item, jokeItem);
                })
                .build();
    }

    @Provides
    @PerActivity
    @Named("jokeDetail")
    UseCase provideGetJokeDetailUseCase(JokeRepository jokeRepository,
                                        ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetJokeDetail(jokeRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("jokeList")
    IterableUseCase provideGetJokeListUseCase(JokeRepository jokeRepository,
                                              ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetJokeList(jokeRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("duoshuoCommentList")
    IterableUseCase provideGetDuoshuoCommentListUseCase(CommentRepository commentRepository,
                                                        ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetDuoshuoCommentList(commentRepository, threadExecutor, postExecutionThread);
    }
}

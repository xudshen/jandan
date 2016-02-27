package info.xudshen.jandan.internal.di.modules;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.design.widget.FloatingActionButton;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVHeaderAdapter;
import info.xudshen.droiddata.adapter.impl.DDBindableViewHolder;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.dao.VideoItemDao;
import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.interactor.GetDuoshuoCommentList;
import info.xudshen.jandan.domain.interactor.GetVideoDetail;
import info.xudshen.jandan.domain.interactor.GetVideoList;
import info.xudshen.jandan.domain.interactor.IterableUseCase;
import info.xudshen.jandan.domain.interactor.UseCase;
import info.xudshen.jandan.domain.interactor.VoteComment;
import info.xudshen.jandan.domain.interactor.VoteVideo;
import info.xudshen.jandan.domain.model.VideoItem;
import info.xudshen.jandan.domain.repository.CommentRepository;
import info.xudshen.jandan.domain.repository.PostRepository;
import info.xudshen.jandan.domain.repository.VideoRepository;
import info.xudshen.jandan.internal.di.PerActivity;

/**
 * Created by xudshen on 16/2/20.
 */
@Module
public class VideoModule {
    private static final Logger logger = LoggerFactory.getLogger(VideoModule.class);

    public VideoModule() {
    }

    @Provides
    @PerActivity
    @Named("videoListAdapter")
    DDBindableCursorLoaderRVHeaderAdapter providePostListAdapter(Activity activity, VideoItemDao videoItemDao) {
        return new DDBindableCursorLoaderRVHeaderAdapter.Builder<DDBindableViewHolder>()
                .cursorLoader(activity, VideoItemDao.CONTENT_URI, null, null, null, VideoItemDao.Properties.Date.columnName + " desc")
                .headerViewHolderCreator((inflater, viewType, parent) -> {
                    return new DDBindableViewHolder(inflater.inflate(
                            com.github.florent37.materialviewpager.R.layout.material_view_pager_placeholder,
                            parent, false));
                })
                .itemViewHolderCreator(((inflater1, viewType1, parent1) -> {
                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater1, viewType1, parent1, false);
                    FloatingActionButton button = (FloatingActionButton) viewDataBinding.getRoot().findViewById(R.id.play_buttom);
                    button.setImageDrawable(new IconicsDrawable(activity)
                            .icon(GoogleMaterial.Icon.gmd_play_circle_filled)
                            .color(activity.getResources().getColor(R.color.md_white_1000))
                            .sizeDp(20)
                            .paddingDp(2));
                    return new DDBindableViewHolder(viewDataBinding);
                }))
                .itemLayoutSelector((position, cursor) -> R.layout.video_card_view)
                .itemViewDataBindingVariableAction((viewDataBinding, cursor) -> {
                    VideoItem videoItem = videoItemDao.loadEntity(cursor);
                    viewDataBinding.setVariable(BR.item, videoItem);
                })
                .build();
    }

    @Provides
    @PerActivity
    @Named("videoDetail")
    UseCase provideGetVideoDetailUseCase(VideoRepository videoRepository,
                                         ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetVideoDetail(videoRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("videoList")
    IterableUseCase provideGetVideoListUseCase(VideoRepository videoRepository,
                                               ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetVideoList(videoRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("duoshuoCommentList")
    IterableUseCase provideGetDuoshuoCommentListUseCase(CommentRepository commentRepository,
                                                        ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetDuoshuoCommentList(commentRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("voteVideo")
    UseCase provideVoteVideoUseCase(VideoRepository videoRepository,
                                    ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new VoteVideo(videoRepository, threadExecutor, postExecutionThread);
    }
}

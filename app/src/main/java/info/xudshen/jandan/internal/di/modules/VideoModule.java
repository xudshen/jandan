package info.xudshen.jandan.internal.di.modules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.interactor.GetDuoshuoCommentList;
import info.xudshen.jandan.domain.interactor.GetVideoDetail;
import info.xudshen.jandan.domain.interactor.GetVideoList;
import info.xudshen.jandan.domain.interactor.IterableUseCase;
import info.xudshen.jandan.domain.interactor.UseCase;
import info.xudshen.jandan.domain.interactor.VoteVideo;
import info.xudshen.jandan.domain.repository.CommentRepository;
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

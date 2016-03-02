package info.xudshen.jandan.internal.di.modules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.interactor.GetPostComment;
import info.xudshen.jandan.domain.interactor.GetPostDetail;
import info.xudshen.jandan.domain.interactor.GetPostList;
import info.xudshen.jandan.domain.interactor.IterableUseCase;
import info.xudshen.jandan.domain.interactor.UseCase;
import info.xudshen.jandan.domain.interactor.VoteComment;
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

    @Provides
    @PerActivity
    @Named("voteComment")
    UseCase provideVoteCommentUseCase(PostRepository postRepository,
                                      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new VoteComment(postRepository, threadExecutor, postExecutionThread);
    }
}

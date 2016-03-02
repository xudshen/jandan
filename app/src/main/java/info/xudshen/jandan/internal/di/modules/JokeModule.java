package info.xudshen.jandan.internal.di.modules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.interactor.GetDuoshuoCommentList;
import info.xudshen.jandan.domain.interactor.GetJokeDetail;
import info.xudshen.jandan.domain.interactor.GetJokeList;
import info.xudshen.jandan.domain.interactor.IterableUseCase;
import info.xudshen.jandan.domain.interactor.UseCase;
import info.xudshen.jandan.domain.interactor.VoteJoke;
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

    @Provides
    @PerActivity
    @Named("voteJoke")
    UseCase provideVoteJokeUseCase(JokeRepository jokeRepository,
                                   ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new VoteJoke(jokeRepository, threadExecutor, postExecutionThread);
    }
}

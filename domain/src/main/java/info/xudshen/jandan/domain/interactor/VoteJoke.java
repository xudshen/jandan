package info.xudshen.jandan.domain.interactor;

import javax.inject.Inject;

import info.xudshen.jandan.domain.enums.VoteType;
import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.repository.JokeRepository;
import info.xudshen.jandan.domain.repository.PostRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/26.
 */
public class VoteJoke extends UseCase {
    private final JokeRepository jokeRepository;

    @Inject
    public VoteJoke(JokeRepository jokeRepository,
                    ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.jokeRepository = jokeRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        throw new UnsupportedOperationException("need pass commentId");
    }

    @Override
    protected Observable buildUseCaseObservable(Object... params) {
        Long commentId = (Long) params[0];
        VoteType voteType = (VoteType) params[1];
        return this.jokeRepository.voteCommonItem(commentId, voteType);
    }
}

package info.xudshen.jandan.domain.interactor;

import javax.inject.Inject;

import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.repository.JokeRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/21.
 */
public class GetJokeDetail extends UseCase {
    private final JokeRepository jokeRepository;

    @Inject
    public GetJokeDetail(JokeRepository jokeRepository,
                         ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.jokeRepository = jokeRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        throw new UnsupportedOperationException("need pass a postId");
    }

    @Override
    protected Observable buildUseCaseObservable(Object... params) {
        Long jokeId = (Long) params[0];
        return this.jokeRepository.joke(jokeId);
    }
}

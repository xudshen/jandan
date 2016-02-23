package info.xudshen.jandan.domain.interactor;

import javax.inject.Inject;

import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.repository.JokeRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/20.
 */
public class GetJokeList extends IterableUseCase {
    private final JokeRepository jokeRepository;

    @Inject
    public GetJokeList(JokeRepository jokeRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.jokeRepository = jokeRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.jokeRepository.jokeList();
    }

    @Override
    protected Observable buildUseCaseObservable(Object... params) {
        throw new UnsupportedOperationException("");
    }

    @Override
    protected Observable buildIterableUseCaseObservable() {
        return this.jokeRepository.jokeListNextPage();
    }

    @Override
    protected Observable buildIterableUseCaseObservable(Object... params) {
        throw new UnsupportedOperationException("");
    }
}

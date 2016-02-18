package info.xudshen.jandan.domain.interactor;

import javax.inject.Inject;

import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.repository.PostRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/17.
 */
public class GetPostList extends IterableUseCase {
    private final PostRepository postRepository;

    @Inject
    public GetPostList(PostRepository postRepository,
                       ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.postRepository = postRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.postRepository.postList();
    }

    @Override
    protected Observable buildUseCaseObservable(Long... params) {
        throw new UnsupportedOperationException("");
    }

    @Override
    protected Observable buildIterableUseCaseObservable() {
        return this.postRepository.postListNextPage();
    }

    @Override
    protected Observable buildIterableUseCaseObservable(Long... params) {
        throw new UnsupportedOperationException("");
    }
}

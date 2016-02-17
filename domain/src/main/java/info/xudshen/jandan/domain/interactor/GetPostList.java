package info.xudshen.jandan.domain.interactor;

import javax.inject.Inject;

import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.repository.PostRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/17.
 */
public class GetPostList extends UseCase {
    private final PostRepository postRepository;

    @Inject
    public GetPostList(PostRepository postRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.postRepository = postRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return null;
    }

    @Override
    protected Observable buildUseCaseObservable(Long... params) {
        Long page = params[0];
        return this.postRepository.postList(page);
    }
}

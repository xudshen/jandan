package info.xudshen.jandan.domain.interactor;

import javax.inject.Inject;

import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.repository.PostRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/16.
 */
public class GetPostDetail extends UseCase {
    private final Long postId;
    private final PostRepository postRepository;

    @Inject
    public GetPostDetail(Long postId, PostRepository postRepository,
                         ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.postId = postId;
        this.postRepository = postRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.postRepository.post(this.postId);
    }
}

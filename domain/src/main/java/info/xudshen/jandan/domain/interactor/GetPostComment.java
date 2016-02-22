package info.xudshen.jandan.domain.interactor;

import javax.inject.Inject;

import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.repository.PostRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/19.
 */
public class GetPostComment extends IterableUseCase {
    private final PostRepository postRepository;

    @Inject
    public GetPostComment(PostRepository postRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.postRepository = postRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        throw new UnsupportedOperationException("need pass a postId");
    }

    @Override
    protected Observable buildUseCaseObservable(Object... params) {
        Long postId = (Long) params[0];
        return this.postRepository.postCommentList(postId);
    }

    @Override
    protected Observable buildIterableUseCaseObservable() {
        throw new UnsupportedOperationException("need pass a postId");
    }

    @Override
    protected Observable buildIterableUseCaseObservable(Object... params) {
        Long postId = (Long) params[0];
        return this.postRepository.postCommentListNext(postId);
    }
}

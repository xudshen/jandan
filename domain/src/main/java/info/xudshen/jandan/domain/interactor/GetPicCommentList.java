package info.xudshen.jandan.domain.interactor;

import javax.inject.Inject;

import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.repository.CommentRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/22.
 */
public class GetPicCommentList extends IterableUseCase {
    private final CommentRepository commentRepository;

    @Inject
    public GetPicCommentList(CommentRepository commentRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.commentRepository = commentRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        throw new UnsupportedOperationException("need pass a threadKey");
    }

    @Override
    protected Observable buildUseCaseObservable(Object... params) {
        String threadKey = (String) params[0];
        return this.commentRepository.picCommentList(threadKey);
    }

    @Override
    protected Observable buildIterableUseCaseObservable() {
        throw new UnsupportedOperationException("need pass a threadKey");
    }

    @Override
    protected Observable buildIterableUseCaseObservable(Object... params) {
        String threadKey = (String) params[0];
        return this.commentRepository.picCommentListNext(threadKey);
    }
}

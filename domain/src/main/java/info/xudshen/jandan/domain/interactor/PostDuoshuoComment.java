package info.xudshen.jandan.domain.interactor;

import javax.inject.Inject;

import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.repository.CommentRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/25.
 */
public class PostDuoshuoComment extends UseCase {
    private final CommentRepository commentRepository;

    @Inject
    public PostDuoshuoComment(CommentRepository commentRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.commentRepository = commentRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        throw new UnsupportedOperationException("need pass postId,name,email,content");
    }

    @Override
    protected Observable buildUseCaseObservable(Object... params) {
        String threadKey = (String) params[0];
        String authorName = (String) params[1];
        String authorEmail = (String) params[2];
        String message = (String) params[3];
        String parentId = (String) params[4];
        return this.commentRepository.postDuoshuoComment(threadKey, authorName, authorEmail, message, parentId);
    }
}

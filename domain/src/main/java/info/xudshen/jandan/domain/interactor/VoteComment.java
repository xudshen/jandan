package info.xudshen.jandan.domain.interactor;

import javax.inject.Inject;

import info.xudshen.jandan.domain.enums.VoteType;
import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.repository.PostRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/26.
 */
public class VoteComment extends UseCase {
    private final PostRepository postRepository;

    @Inject
    public VoteComment(PostRepository postRepository,
                       ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.postRepository = postRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        throw new UnsupportedOperationException("need pass commentId");
    }

    @Override
    protected Observable buildUseCaseObservable(Object... params) {
        Long commentId = (Long) params[0];
        VoteType voteType = (VoteType) params[1];
        return this.postRepository.voteComment(commentId, voteType);
    }
}

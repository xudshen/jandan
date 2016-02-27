package info.xudshen.jandan.domain.interactor;

import javax.inject.Inject;

import info.xudshen.jandan.domain.enums.VoteType;
import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.repository.JokeRepository;
import info.xudshen.jandan.domain.repository.PicRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/26.
 */
public class VotePic extends UseCase {
    private final PicRepository picRepository;

    @Inject
    public VotePic(PicRepository picRepository,
                   ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.picRepository = picRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        throw new UnsupportedOperationException("need pass commentId");
    }

    @Override
    protected Observable buildUseCaseObservable(Object... params) {
        Long commentId = (Long) params[0];
        VoteType voteType = (VoteType) params[1];
        return this.picRepository.voteCommonItem(commentId, voteType);
    }
}

package info.xudshen.jandan.domain.interactor;

import javax.inject.Inject;

import info.xudshen.jandan.domain.enums.VoteType;
import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.repository.PicRepository;
import info.xudshen.jandan.domain.repository.VideoRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/26.
 */
public class VoteVideo extends UseCase {
    private final VideoRepository videoRepository;

    @Inject
    public VoteVideo(VideoRepository videoRepository,
                     ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.videoRepository = videoRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        throw new UnsupportedOperationException("need pass commentId");
    }

    @Override
    protected Observable buildUseCaseObservable(Object... params) {
        Long commentId = (Long) params[0];
        VoteType voteType = (VoteType) params[1];
        return this.videoRepository.voteCommonItem(commentId, voteType);
    }
}

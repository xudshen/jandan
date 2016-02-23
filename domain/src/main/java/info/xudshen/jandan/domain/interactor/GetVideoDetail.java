package info.xudshen.jandan.domain.interactor;

import javax.inject.Inject;

import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.repository.VideoRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/21.
 */
public class GetVideoDetail extends UseCase {
    private final VideoRepository videoRepository;

    @Inject
    public GetVideoDetail(VideoRepository videoRepository,
                          ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.videoRepository = videoRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        throw new UnsupportedOperationException("need pass a postId");
    }

    @Override
    protected Observable buildUseCaseObservable(Object... params) {
        Long videoId = (Long) params[0];
        return this.videoRepository.video(videoId);
    }
}

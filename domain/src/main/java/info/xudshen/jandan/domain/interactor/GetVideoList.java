package info.xudshen.jandan.domain.interactor;

import javax.inject.Inject;

import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.repository.VideoRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/20.
 */
public class GetVideoList extends IterableUseCase {
    private final VideoRepository videoRepository;

    @Inject
    public GetVideoList(VideoRepository videoRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.videoRepository = videoRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.videoRepository.videoList();
    }

    @Override
    protected Observable buildUseCaseObservable(Object... params) {
        throw new UnsupportedOperationException("");
    }

    @Override
    protected Observable buildIterableUseCaseObservable() {
        return this.videoRepository.videoListNextPage();
    }

    @Override
    protected Observable buildIterableUseCaseObservable(Object... params) {
        throw new UnsupportedOperationException("");
    }
}

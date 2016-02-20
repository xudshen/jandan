package info.xudshen.jandan.domain.interactor;

import javax.inject.Inject;

import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.repository.PicRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/20.
 */
public class GetPicList extends IterableUseCase {
    private final PicRepository picRepository;

    @Inject
    public GetPicList(PicRepository picRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.picRepository = picRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.picRepository.picList();
    }

    @Override
    protected Observable buildUseCaseObservable(Long... params) {
        throw new UnsupportedOperationException("");
    }

    @Override
    protected Observable buildIterableUseCaseObservable() {
        return this.picRepository.picListNextPage();
    }

    @Override
    protected Observable buildIterableUseCaseObservable(Long... params) {
        throw new UnsupportedOperationException("");
    }
}

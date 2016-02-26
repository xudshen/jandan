package info.xudshen.jandan.domain.interactor;

import javax.inject.Inject;

import info.xudshen.jandan.domain.enums.ReaderItemType;
import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.repository.FavoRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/26.
 */
public class DeleteFavoItem extends UseCase {
    private final FavoRepository favoRepository;

    @Inject
    public DeleteFavoItem(FavoRepository favoRepository,
                          ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.favoRepository = favoRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        throw new UnsupportedOperationException("need pass favoitem");
    }

    @Override
    protected Observable buildUseCaseObservable(Object... params) {
        ReaderItemType type = (ReaderItemType) params[0];
        String actualId = (String) params[1];
        return this.favoRepository.deleteFavoItem(type, actualId);
    }
}

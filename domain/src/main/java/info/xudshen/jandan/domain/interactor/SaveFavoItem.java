package info.xudshen.jandan.domain.interactor;

import javax.inject.Inject;

import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.model.FavoItem;
import info.xudshen.jandan.domain.repository.FavoRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/26.
 */
public class SaveFavoItem extends UseCase {
    private final FavoRepository favoRepository;

    @Inject
    public SaveFavoItem(FavoRepository favoRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.favoRepository = favoRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        throw new UnsupportedOperationException("need pass favoitem");
    }

    @Override
    protected Observable buildUseCaseObservable(Object... params) {
        FavoItem favoItem = (FavoItem) params[0];
        return this.favoRepository.saveFavoItem(favoItem);
    }
}

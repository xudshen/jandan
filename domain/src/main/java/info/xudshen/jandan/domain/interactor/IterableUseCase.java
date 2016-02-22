package info.xudshen.jandan.domain.interactor;

import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by xudshen on 16/2/18.
 */
public abstract class IterableUseCase extends UseCase {
    public IterableUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    protected abstract Observable buildIterableUseCaseObservable();

    protected abstract Observable buildIterableUseCaseObservable(Object... params);

    @SuppressWarnings("unchecked")
    public void executeNext(Observable.Transformer transformer, Subscriber UseCaseSubscriber) {
        this.buildIterableUseCaseObservable()
                .compose(transformer)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(UseCaseSubscriber);
    }

    @SuppressWarnings("unchecked")
    public void executeNext(Observable.Transformer transformer, Subscriber UseCaseSubscriber,
                            Object... params) {
        this.buildIterableUseCaseObservable(params)
                .compose(transformer)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(UseCaseSubscriber);
    }
}

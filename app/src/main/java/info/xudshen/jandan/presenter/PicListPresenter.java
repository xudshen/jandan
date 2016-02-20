package info.xudshen.jandan.presenter;

import android.support.annotation.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

import info.xudshen.jandan.domain.interactor.IterableUseCase;
import info.xudshen.jandan.internal.di.PerActivity;
import info.xudshen.jandan.view.DataListView;
import rx.Subscriber;

/**
 * Created by xudshen on 16/2/20.
 */
@PerActivity
public class PicListPresenter implements Presenter {
    private static final Logger logger = LoggerFactory.getLogger(PicListPresenter.class);

    private DataListView dataListView;
    private IterableUseCase getPicListUseCase;

    @Inject
    public PicListPresenter(@Named("picList") IterableUseCase getPicListUseCase) {
        this.getPicListUseCase = getPicListUseCase;
    }

    public void setView(@NonNull DataListView dataListView) {
        this.dataListView = dataListView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.dataListView = null;
    }

    public void initialize() {
        this.dataListView.showLoading();
        this.getPicListUseCase.execute(this.dataListView.bindToLifecycle(),
                new Subscriber() {
                    @Override
                    public void onCompleted() {
                        PicListPresenter.this.dataListView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.error("", e);
                    }

                    @Override
                    public void onNext(Object o) {
                        PicListPresenter.this.dataListView.renderList();
                    }
                });
    }

    /**
     * load new data
     */
    public void swipeDownStart() {
        initialize();
    }

    /**
     * load history data
     */
    public void swipeUpStart() {
        this.dataListView.showSwipeUpLoading();
        this.getPicListUseCase.executeNext(this.dataListView.bindToLifecycle(),
                new Subscriber() {
                    @Override
                    public void onCompleted() {
                        PicListPresenter.this.dataListView.hideSwipeUpLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.error("", e);
                    }

                    @Override
                    public void onNext(Object o) {
                        PicListPresenter.this.dataListView.renderList();
                    }
                });
    }
}
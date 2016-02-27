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
 * Created by xudshen on 16/1/7.
 */
@PerActivity
public class PostListPresenter implements Presenter {
    private static final Logger logger = LoggerFactory.getLogger(PostListPresenter.class);

    private DataListView dataListView;

    private IterableUseCase getPostListUseCase;

    @Inject
    public PostListPresenter(@Named("postList") IterableUseCase getPostListUseCase) {
        this.getPostListUseCase = getPostListUseCase;
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
        this.getPostListUseCase.execute(this.dataListView.bindToLifecycle(),
                new Subscriber() {
                    @Override
                    public void onCompleted() {
                        PostListPresenter.this.dataListView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.error("", e);
                    }

                    @Override
                    public void onNext(Object o) {
                        PostListPresenter.this.dataListView.renderDataList();
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
        this.dataListView.showLoadingMore();
        this.getPostListUseCase.executeNext(this.dataListView.bindToLifecycle(),
                new Subscriber() {
                    @Override
                    public void onCompleted() {
                        PostListPresenter.this.dataListView.hideLoadingMore();
                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.error("", e);
                    }

                    @Override
                    public void onNext(Object o) {
                        PostListPresenter.this.dataListView.renderDataList();
                    }
                });
    }
}

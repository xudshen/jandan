package info.xudshen.jandan.presenter;

import android.support.annotation.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

import info.xudshen.jandan.domain.interactor.IterableUseCase;
import info.xudshen.jandan.internal.di.PerActivity;
import info.xudshen.jandan.view.PostListView;
import rx.Subscriber;

/**
 * Created by xudshen on 16/1/7.
 */
@PerActivity
public class PostListPresenter implements Presenter {
    private static final Logger logger = LoggerFactory.getLogger(PostListPresenter.class);

    private PostListView postListView;

    private IterableUseCase getPostListUseCase;

    @Inject
    public PostListPresenter(@Named("postList") IterableUseCase getPostListUseCase) {
        this.getPostListUseCase = getPostListUseCase;
    }

    public void setView(@NonNull PostListView view) {
        this.postListView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.postListView = null;
    }

    public void initialize() {
        this.postListView.showLoading();
        this.getPostListUseCase.execute(this.postListView.bindToLifecycle(),
                new Subscriber() {
                    @Override
                    public void onCompleted() {
                        PostListPresenter.this.postListView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.error("", e);
                    }

                    @Override
                    public void onNext(Object o) {
                        PostListPresenter.this.postListView.renderList();
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
        this.postListView.showSwipeUpLoading();
        this.getPostListUseCase.executeNext(this.postListView.bindToLifecycle(),
                new Subscriber() {
                    @Override
                    public void onCompleted() {
                        PostListPresenter.this.postListView.hideSwipeUpLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.error("", e);
                    }

                    @Override
                    public void onNext(Object o) {
                        PostListPresenter.this.postListView.renderList();
                    }
                });
    }
}

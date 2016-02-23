package info.xudshen.jandan.presenter;

import android.support.annotation.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import info.xudshen.jandan.data.constants.Constants;
import info.xudshen.jandan.domain.interactor.IterableUseCase;
import info.xudshen.jandan.domain.interactor.UseCase;
import info.xudshen.jandan.domain.model.DuoshuoComment;
import info.xudshen.jandan.domain.model.JokeItem;
import info.xudshen.jandan.view.DataDetailView;
import rx.Subscriber;

public class JokeDetailPresenter implements Presenter {
    private static final Logger logger = LoggerFactory.getLogger(JokeDetailPresenter.class);
    private DataDetailView<JokeItem> dataDetailView;

    private final UseCase getJokeDetailUseCase;
    private final IterableUseCase getDuoshuoCommentListUseCase;

    @Inject
    public JokeDetailPresenter(@Named("jokeDetail") UseCase getJokeDetailUseCase,
                               @Named("duoshuoCommentList") IterableUseCase getDuoshuoCommentListUseCase) {
        this.getJokeDetailUseCase = getJokeDetailUseCase;
        this.getDuoshuoCommentListUseCase = getDuoshuoCommentListUseCase;
    }

    public void setView(@NonNull DataDetailView<JokeItem> dataDetailView) {
        this.dataDetailView = dataDetailView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.dataDetailView = null;
    }

    public void initialize(Long postId) {
        this.loadJokeDetail(postId);
    }

    public void refreshComment(Long jokeId) {
        this.dataDetailView.showSwipeUpLoading();
        this.getDuoshuoCommentListUseCase.executeNext(this.dataDetailView.bindToLifecycle(),
                new Subscriber<List<DuoshuoComment>>() {
                    @Override
                    public void onCompleted() {
                        JokeDetailPresenter.this.dataDetailView.hideSwipeUpLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        JokeDetailPresenter.this.dataDetailView.hideSwipeUpLoading();
                        JokeDetailPresenter.this.dataDetailView.showError("");
                    }

                    @Override
                    public void onNext(List<DuoshuoComment> o) {

                    }
                }, Constants.THREAD_PREFIX + jokeId);
    }

    /**
     * Loads user details.
     */
    private void loadJokeDetail(Long postId) {
        this.dataDetailView.showLoading();
        this.getJokeDetailUseCase.execute(this.dataDetailView.bindToLifecycle(),
                new Subscriber<JokeItem>() {
                    @Override
                    public void onCompleted() {
                        JokeDetailPresenter.this.dataDetailView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        JokeDetailPresenter.this.dataDetailView.hideLoading();
                        JokeDetailPresenter.this.dataDetailView.showRetry();
                        //do something
                        logger.error("", e);
                    }

                    @Override
                    public void onNext(JokeItem jokeItem) {
                        JokeDetailPresenter.this.dataDetailView.renderItemDetail(jokeItem);
                    }
                }, postId);
    }
}

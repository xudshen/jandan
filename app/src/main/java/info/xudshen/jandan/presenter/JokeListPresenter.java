package info.xudshen.jandan.presenter;

import android.support.annotation.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

import info.xudshen.jandan.domain.enums.VoteResult;
import info.xudshen.jandan.domain.enums.VoteType;
import info.xudshen.jandan.domain.interactor.IterableUseCase;
import info.xudshen.jandan.domain.interactor.UseCase;
import info.xudshen.jandan.internal.di.PerActivity;
import info.xudshen.jandan.view.ActionView;
import info.xudshen.jandan.view.DataListView;
import rx.Subscriber;

/**
 * Created by xudshen on 16/2/20.
 */
@PerActivity
public class JokeListPresenter implements Presenter {
    private static final Logger logger = LoggerFactory.getLogger(JokeListPresenter.class);

    private DataListView dataListView;
    private IterableUseCase getJokeListUseCase;

    private ActionView voteCommentView;
    private final UseCase voteCommentUseCase;

    @Inject
    public JokeListPresenter(@Named("jokeList") IterableUseCase getJokeListUseCase,
                             @Named("voteJoke") UseCase voteCommentUseCase) {
        this.getJokeListUseCase = getJokeListUseCase;
        this.voteCommentUseCase = voteCommentUseCase;
    }

    public void setView(@NonNull DataListView dataListView) {
        this.dataListView = dataListView;
    }

    public void setVoteCommentView(@NonNull ActionView voteCommentView) {
        this.voteCommentView = voteCommentView;
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
        this.voteCommentView = null;
    }

    public void initialize() {
        this.dataListView.showLoading();
        this.getJokeListUseCase.execute(this.dataListView.bindToLifecycle(),
                new Subscriber() {
                    @Override
                    public void onCompleted() {
                        JokeListPresenter.this.dataListView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.error("", e);
                    }

                    @Override
                    public void onNext(Object o) {
                        JokeListPresenter.this.dataListView.renderList();
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
        this.getJokeListUseCase.executeNext(this.dataListView.bindToLifecycle(),
                new Subscriber() {
                    @Override
                    public void onCompleted() {
                        JokeListPresenter.this.dataListView.hideSwipeUpLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.error("", e);
                    }

                    @Override
                    public void onNext(Object o) {
                        JokeListPresenter.this.dataListView.renderList();
                    }
                });
    }

    public void voteComment(Long commentId, VoteType voteType) {
        this.voteCommentView.showLoading();
        this.voteCommentUseCase.execute(this.dataListView.bindToLifecycle(),
                new Subscriber<VoteResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.error("{}", e);
                        voteCommentView.hideLoading();
                        voteCommentView.showError("");
                    }

                    @Override
                    public void onNext(VoteResult voteResult) {
                        if (voteResult == VoteResult.Thanks) {
                            voteCommentView.hideLoading();
                            voteCommentView.showSuccess();
                        } else {
                            voteCommentView.showError("已经发表过意见噜");
                        }
                    }
                }, commentId, voteType);
    }
}

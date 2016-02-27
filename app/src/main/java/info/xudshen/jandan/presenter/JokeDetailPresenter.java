package info.xudshen.jandan.presenter;

import android.support.annotation.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import info.xudshen.jandan.data.constants.Constants;
import info.xudshen.jandan.data.dao.ModelTrans;
import info.xudshen.jandan.data.model.observable.JokeItemObservable;
import info.xudshen.jandan.domain.enums.VoteResult;
import info.xudshen.jandan.domain.enums.VoteType;
import info.xudshen.jandan.domain.interactor.IterableUseCase;
import info.xudshen.jandan.domain.interactor.UseCase;
import info.xudshen.jandan.domain.model.DuoshuoComment;
import info.xudshen.jandan.domain.model.FavoItem;
import info.xudshen.jandan.domain.model.FavoItemTrans;
import info.xudshen.jandan.domain.model.JokeItem;
import info.xudshen.jandan.view.ActionView;
import info.xudshen.jandan.view.DataDetailView;
import rx.Subscriber;

public class JokeDetailPresenter implements Presenter {
    private static final Logger logger = LoggerFactory.getLogger(JokeDetailPresenter.class);
    private DataDetailView<JokeItemObservable> dataDetailView;

    private final UseCase getJokeDetailUseCase;
    private final IterableUseCase getDuoshuoCommentListUseCase;
    private final ModelTrans modelTrans;

    private ActionView voteCommentView;
    private final UseCase voteCommentUseCase;

    @Inject
    public JokeDetailPresenter(@Named("jokeDetail") UseCase getJokeDetailUseCase,
                               @Named("duoshuoCommentList") IterableUseCase getDuoshuoCommentListUseCase,
                               @Named("voteJoke") UseCase voteCommentUseCase,
                               ModelTrans modelTrans) {
        this.getJokeDetailUseCase = getJokeDetailUseCase;
        this.getDuoshuoCommentListUseCase = getDuoshuoCommentListUseCase;
        this.voteCommentUseCase = voteCommentUseCase;
        this.modelTrans = modelTrans;
    }

    public void setView(@NonNull DataDetailView<JokeItemObservable> dataDetailView) {
        this.dataDetailView = dataDetailView;
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
        this.dataDetailView = null;
        this.voteCommentView = null;
    }

    public void initialize(Long jokeId) {
        this.loadJokeDetail(jokeId);
    }

    public void refreshComment(Long jokeId) {
        this.dataDetailView.showLoadingMore();
        this.getDuoshuoCommentListUseCase.executeNext(this.dataDetailView.bindToLifecycle(),
                new Subscriber<List<DuoshuoComment>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        JokeDetailPresenter.this.dataDetailView.hideLoadingMore(-1);
                        JokeDetailPresenter.this.dataDetailView.showError("");
                    }

                    @Override
                    public void onNext(List<DuoshuoComment> o) {
                        JokeDetailPresenter.this.dataDetailView.hideLoadingMore(o.size());
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
                        JokeItemObservable jokeItemObservable = modelTrans.transJokeItem(jokeItem);
                        JokeDetailPresenter.this.dataDetailView.renderItemDetail(jokeItemObservable);
                    }
                }, postId);
    }

    public void initialize(FavoItem favoItem) {
        JokeItemObservable jokeItemObservable = modelTrans.transJokeItem(FavoItemTrans.toJokeItem(favoItem));
        this.dataDetailView.renderItemDetail(jokeItemObservable);
        JokeDetailPresenter.this.dataDetailView.hideLoading();
    }

    public void voteComment(Long commentId, VoteType voteType) {
        this.voteCommentView.showLoading();
        this.voteCommentUseCase.execute(this.dataDetailView.bindToLifecycle(),
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

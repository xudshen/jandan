package info.xudshen.jandan.presenter;

import android.support.annotation.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import info.xudshen.jandan.data.constants.Constants;
import info.xudshen.jandan.data.dao.ModelTrans;
import info.xudshen.jandan.data.model.observable.PicItemObservable;
import info.xudshen.jandan.domain.enums.VoteResult;
import info.xudshen.jandan.domain.enums.VoteType;
import info.xudshen.jandan.domain.interactor.IterableUseCase;
import info.xudshen.jandan.domain.interactor.UseCase;
import info.xudshen.jandan.domain.model.DuoshuoComment;
import info.xudshen.jandan.domain.model.FavoItem;
import info.xudshen.jandan.domain.model.FavoItemTrans;
import info.xudshen.jandan.domain.model.PicItem;
import info.xudshen.jandan.view.ActionView;
import info.xudshen.jandan.view.DataDetailView;
import rx.Subscriber;

public class PicDetailPresenter implements Presenter {
    private static final Logger logger = LoggerFactory.getLogger(PicDetailPresenter.class);
    private DataDetailView<PicItemObservable> dataDetailView;

    private final UseCase getPicDetailUseCase;
    private final IterableUseCase getDuoshuoCommentListUseCase;
    private final ModelTrans modelTrans;

    private ActionView voteCommentView;
    private final UseCase voteCommentUseCase;

    @Inject
    public PicDetailPresenter(@Named("picDetail") UseCase getPicDetailUseCase,
                              @Named("duoshuoCommentList") IterableUseCase getDuoshuoCommentListUseCase,
                              @Named("votePic") UseCase voteCommentUseCase,
                              ModelTrans modelTrans) {
        this.getPicDetailUseCase = getPicDetailUseCase;
        this.getDuoshuoCommentListUseCase = getDuoshuoCommentListUseCase;
        this.voteCommentUseCase = voteCommentUseCase;
        this.modelTrans = modelTrans;
    }

    public void setView(@NonNull DataDetailView<PicItemObservable> dataDetailView) {
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

    public void initialize(Long picId) {
        this.loadPicDetail(picId);
    }

    public void refreshComment(Long picId) {
        this.dataDetailView.showLoadingMore();
        this.getDuoshuoCommentListUseCase.executeNext(this.dataDetailView.bindToLifecycle(),
                new Subscriber<List<DuoshuoComment>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        PicDetailPresenter.this.dataDetailView.hideLoadingMore(-1);
                        PicDetailPresenter.this.dataDetailView.showError("");
                    }

                    @Override
                    public void onNext(List<DuoshuoComment> o) {
                        PicDetailPresenter.this.dataDetailView.hideLoadingMore(o.size());
                    }
                }, Constants.THREAD_PREFIX + picId);
    }

    /**
     * Loads user details.
     */
    private void loadPicDetail(Long postId) {
        this.dataDetailView.showLoading();
        this.getPicDetailUseCase.execute(this.dataDetailView.bindToLifecycle(),
                new Subscriber<PicItem>() {
                    @Override
                    public void onCompleted() {
                        PicDetailPresenter.this.dataDetailView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        PicDetailPresenter.this.dataDetailView.hideLoading();
                        PicDetailPresenter.this.dataDetailView.showRetry();
                        //do something
                        logger.error("", e);
                    }

                    @Override
                    public void onNext(PicItem picItem) {
                        PicItemObservable picItemObservable = modelTrans.transPicItem(picItem);
                        PicDetailPresenter.this.dataDetailView.renderDataDetail(picItemObservable);
                    }
                }, postId);
    }

    public void initialize(FavoItem favoItem) {
        PicItemObservable picItemObservable = modelTrans.transPicItem(FavoItemTrans.toPicItem(favoItem));
        this.dataDetailView.renderDataDetail(picItemObservable);
        PicDetailPresenter.this.dataDetailView.hideLoading();
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

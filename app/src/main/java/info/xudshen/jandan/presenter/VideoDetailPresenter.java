package info.xudshen.jandan.presenter;

import android.support.annotation.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import info.xudshen.jandan.data.constants.Constants;
import info.xudshen.jandan.data.dao.ModelTrans;
import info.xudshen.jandan.data.model.observable.VideoItemObservable;
import info.xudshen.jandan.domain.enums.VoteResult;
import info.xudshen.jandan.domain.enums.VoteType;
import info.xudshen.jandan.domain.interactor.IterableUseCase;
import info.xudshen.jandan.domain.interactor.UseCase;
import info.xudshen.jandan.domain.model.DuoshuoComment;
import info.xudshen.jandan.domain.model.FavoItem;
import info.xudshen.jandan.domain.model.FavoItemTrans;
import info.xudshen.jandan.domain.model.VideoItem;
import info.xudshen.jandan.utils.RetrofitErrorHelper;
import info.xudshen.jandan.view.ActionView;
import info.xudshen.jandan.view.DataDetailView;
import rx.Subscriber;

public class VideoDetailPresenter implements Presenter {
    private static final Logger logger = LoggerFactory.getLogger(VideoDetailPresenter.class);
    private DataDetailView<VideoItemObservable> dataDetailView;

    private final UseCase getVideoDetailUseCase;
    private final IterableUseCase getDuoshuoCommentListUseCase;
    private final ModelTrans modelTrans;

    private ActionView voteCommentView;
    private final UseCase voteCommentUseCase;

    @Inject
    public VideoDetailPresenter(@Named("videoDetail") UseCase getVideoDetailUseCase,
                                @Named("duoshuoCommentList") IterableUseCase getDuoshuoCommentListUseCase,
                                @Named("voteVideo") UseCase voteCommentUseCase,
                                ModelTrans modelTrans) {
        this.getVideoDetailUseCase = getVideoDetailUseCase;
        this.getDuoshuoCommentListUseCase = getDuoshuoCommentListUseCase;
        this.voteCommentUseCase = voteCommentUseCase;
        this.modelTrans = modelTrans;
    }

    public void setView(@NonNull DataDetailView<VideoItemObservable> dataDetailView) {
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

    public void initialize(Long videoId) {
        this.loadVideoDetail(videoId);
    }

    public void refreshComment(Long videoId) {
        this.dataDetailView.showLoadingMore();
        this.getDuoshuoCommentListUseCase.executeNext(this.dataDetailView.bindToLifecycle(),
                new Subscriber<List<DuoshuoComment>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        VideoDetailPresenter.this.dataDetailView.hideLoadingMore(-1);
                        VideoDetailPresenter.this.dataDetailView.showError(RetrofitErrorHelper.transException(
                                VideoDetailPresenter.this.dataDetailView.context(), e));
                    }

                    @Override
                    public void onNext(List<DuoshuoComment> o) {
                        VideoDetailPresenter.this.dataDetailView.hideLoadingMore(o.size());
                    }
                }, Constants.THREAD_PREFIX + videoId);
    }

    /**
     * Loads user details.
     */
    private void loadVideoDetail(Long postId) {
        this.dataDetailView.showLoading();
        this.getVideoDetailUseCase.execute(this.dataDetailView.bindToLifecycle(),
                new Subscriber<VideoItem>() {
                    @Override
                    public void onCompleted() {
                        VideoDetailPresenter.this.dataDetailView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        VideoDetailPresenter.this.dataDetailView.hideLoading();
                        VideoDetailPresenter.this.dataDetailView.showRetry();
                        //do something
                        logger.error("", e);
                    }

                    @Override
                    public void onNext(VideoItem videoItem) {
                        VideoItemObservable videoItemObservable = modelTrans.transVideoItem(videoItem);
                        VideoDetailPresenter.this.dataDetailView.renderDataDetail(videoItemObservable);
                    }
                }, postId);
    }

    public void initialize(FavoItem favoItem) {
        VideoItemObservable videoItemObservable = modelTrans.transVideoItem(FavoItemTrans.toVideoItem(favoItem));
        this.dataDetailView.renderDataDetail(videoItemObservable);
        VideoDetailPresenter.this.dataDetailView.hideLoading();
    }

    public void voteComment(Long commentId, VoteType voteType) {
        this.voteCommentView.showLoading();
        this.voteCommentUseCase.execute(this.dataDetailView.bindToLifecycle(),
                new Subscriber<VoteResult>() {
                    @Override
                    public void onCompleted() {
                        voteCommentView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        voteCommentView.hideLoading();
                        VideoDetailPresenter.this.voteCommentView.showError(RetrofitErrorHelper.transException(
                                VideoDetailPresenter.this.voteCommentView.context(), e));
                    }

                    @Override
                    public void onNext(VoteResult voteResult) {
                        if (voteResult == VoteResult.Thanks) {
                            voteCommentView.showSuccess();
                        } else {
                            voteCommentView.showError(voteCommentView.context().getString(info.xudshen.jandan.R.string.vote_already));
                        }
                    }
                }, commentId, voteType);
    }
}

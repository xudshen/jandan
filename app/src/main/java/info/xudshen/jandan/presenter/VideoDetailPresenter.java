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
import info.xudshen.jandan.domain.model.VideoItem;
import info.xudshen.jandan.view.DataDetailView;
import rx.Subscriber;

public class VideoDetailPresenter implements Presenter {
    private static final Logger logger = LoggerFactory.getLogger(VideoDetailPresenter.class);
    private DataDetailView<VideoItem> dataDetailView;

    private final UseCase getVideoDetailUseCase;
    private final IterableUseCase getDuoshuoCommentListUseCase;

    @Inject
    public VideoDetailPresenter(@Named("videoDetail") UseCase getVideoDetailUseCase,
                                @Named("duoshuoCommentList") IterableUseCase getDuoshuoCommentListUseCase) {
        this.getVideoDetailUseCase = getVideoDetailUseCase;
        this.getDuoshuoCommentListUseCase = getDuoshuoCommentListUseCase;
    }

    public void setView(@NonNull DataDetailView<VideoItem> dataDetailView) {
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
        this.loadVideoDetail(postId);
    }

    public void refreshComment(Long videoId) {
        this.dataDetailView.showSwipeUpLoading();
        this.getDuoshuoCommentListUseCase.executeNext(this.dataDetailView.bindToLifecycle(),
                new Subscriber<List<DuoshuoComment>>() {
                    @Override
                    public void onCompleted() {
                        VideoDetailPresenter.this.dataDetailView.hideSwipeUpLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        VideoDetailPresenter.this.dataDetailView.hideSwipeUpLoading();
                        VideoDetailPresenter.this.dataDetailView.showError("");
                    }

                    @Override
                    public void onNext(List<DuoshuoComment> o) {

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
                        VideoDetailPresenter.this.dataDetailView.renderItemDetail(videoItem);
                    }
                }, postId);
    }
}

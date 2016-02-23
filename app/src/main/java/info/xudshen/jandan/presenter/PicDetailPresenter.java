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
import info.xudshen.jandan.domain.model.PicItem;
import info.xudshen.jandan.view.DataDetailView;
import rx.Subscriber;

public class PicDetailPresenter implements Presenter {
    private static final Logger logger = LoggerFactory.getLogger(PicDetailPresenter.class);
    private DataDetailView<PicItem> dataDetailView;

    private final UseCase getPicDetailUseCase;
    private final IterableUseCase getDuoshuoCommentListUseCase;

    @Inject
    public PicDetailPresenter(@Named("picDetail") UseCase getPicDetailUseCase,
                              @Named("duoshuoCommentList") IterableUseCase getDuoshuoCommentListUseCase) {
        this.getPicDetailUseCase = getPicDetailUseCase;
        this.getDuoshuoCommentListUseCase = getDuoshuoCommentListUseCase;
    }

    public void setView(@NonNull DataDetailView<PicItem> dataDetailView) {
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
        this.loadPicDetail(postId);
    }

    public void refreshComment(Long picId) {
        this.dataDetailView.showSwipeUpLoading();
        this.getDuoshuoCommentListUseCase.executeNext(this.dataDetailView.bindToLifecycle(),
                new Subscriber<List<DuoshuoComment>>() {
                    @Override
                    public void onCompleted() {
                        PicDetailPresenter.this.dataDetailView.hideSwipeUpLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        PicDetailPresenter.this.dataDetailView.hideSwipeUpLoading();
                        PicDetailPresenter.this.dataDetailView.showError("");
                    }

                    @Override
                    public void onNext(List<DuoshuoComment> o) {

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
                        PicDetailPresenter.this.dataDetailView.renderItemDetail(picItem);
                    }
                }, postId);
    }
}

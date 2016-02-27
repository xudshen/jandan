package info.xudshen.jandan.presenter;

import android.support.annotation.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import info.xudshen.jandan.data.dao.ModelTrans;
import info.xudshen.jandan.data.model.observable.PostObservable;
import info.xudshen.jandan.domain.enums.VoteResult;
import info.xudshen.jandan.domain.enums.VoteType;
import info.xudshen.jandan.domain.interactor.IterableUseCase;
import info.xudshen.jandan.domain.interactor.UseCase;
import info.xudshen.jandan.domain.model.Comment;
import info.xudshen.jandan.domain.model.Post;
import info.xudshen.jandan.view.ActionView;
import info.xudshen.jandan.view.DataDetailView;
import info.xudshen.jandan.view.fragment.PostDetailFragment;
import rx.Subscriber;

/**
 * Created by xudshen on 16/2/16.
 */
//@PerActivity remove this since we need different presenter for each fragment
public class PostDetailPresenter implements Presenter {
    private static final Logger logger = LoggerFactory.getLogger(PostDetailPresenter.class);
    private DataDetailView<PostObservable> dataDetailView;
    private ActionView voteCommentView;

    private final UseCase getPostDetailUseCase;
    private final IterableUseCase getPostCommentUseCase;
    private final UseCase voteCommentUseCase;
    private final ModelTrans modelTrans;

    @Inject
    public PostDetailPresenter(@Named("postDetail") UseCase getPostDetailUseCase,
                               @Named("postComment") IterableUseCase getPostCommentUseCase,
                               @Named("voteComment") UseCase voteCommentUseCase,
                               ModelTrans modelTrans) {
        this.getPostDetailUseCase = getPostDetailUseCase;
        this.getPostCommentUseCase = getPostCommentUseCase;
        this.voteCommentUseCase = voteCommentUseCase;
        this.modelTrans = modelTrans;
    }

    public void setView(@NonNull DataDetailView<PostObservable> dataDetailView) {
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

    public void initialize(Long postId) {
        this.loadPostDetail(postId);
    }

    public void refreshComment(Long postId) {
        this.dataDetailView.showLoadingMore();
        this.getPostCommentUseCase.executeNext(this.dataDetailView.bindToLifecycle(),
                new Subscriber<List<Comment>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        PostDetailPresenter.this.dataDetailView.hideLoadingMore(-1);
                        PostDetailPresenter.this.dataDetailView.showError("");
                    }

                    @Override
                    public void onNext(List<Comment> comments) {
                        PostDetailPresenter.this.dataDetailView.hideLoadingMore(comments.size());
                    }
                }, postId);
    }

    /**
     * Loads user details.
     */
    private void loadPostDetail(Long postId) {
        this.dataDetailView.showLoading();
        this.getPostDetailUseCase.execute(this.dataDetailView.bindToLifecycle(),
                new Subscriber<Post>() {
                    @Override
                    public void onCompleted() {
                        PostDetailPresenter.this.dataDetailView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        PostDetailPresenter.this.dataDetailView.hideLoading();
                        PostDetailPresenter.this.dataDetailView.showRetry();
                        //do something
                        logger.error("", e);
                    }

                    @Override
                    public void onNext(Post post) {
                        PostObservable postObservable = PostDetailPresenter.this.modelTrans.transPost(post);
                        PostDetailPresenter.this.dataDetailView.renderItemDetail(postObservable);
                    }
                }, postId);
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
                        PostDetailPresenter.this.voteCommentView.hideLoading();
                        PostDetailPresenter.this.voteCommentView.showError("");
                    }

                    @Override
                    public void onNext(VoteResult voteResult) {
                        if (voteResult == VoteResult.Thanks) {
                            PostDetailPresenter.this.voteCommentView.hideLoading();
                            PostDetailPresenter.this.voteCommentView.showSuccess();
                        } else {
                            PostDetailPresenter.this.voteCommentView.showError("已经发表过意见噜");
                        }
                    }
                }, commentId, voteType);
    }
}

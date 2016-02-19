package info.xudshen.jandan.presenter;

import android.support.annotation.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import info.xudshen.jandan.domain.interactor.UseCase;
import info.xudshen.jandan.domain.model.Comment;
import info.xudshen.jandan.domain.model.Post;
import info.xudshen.jandan.view.PostDetailView;
import rx.Subscriber;

/**
 * Created by xudshen on 16/2/16.
 */
//@PerActivity remove this since we need different presenter for each fragment
public class PostDetailPresenter implements Presenter {
    private static final Logger logger = LoggerFactory.getLogger(PostDetailPresenter.class);
    private PostDetailView postDetailView;

    private final UseCase getPostDetailUseCase;
    private final UseCase getPostCommentUseCase;

    @Inject
    public PostDetailPresenter(@Named("postDetail") UseCase getPostDetailUseCase,
                               @Named("postComment") UseCase getPostCommentUseCase) {
        this.getPostDetailUseCase = getPostDetailUseCase;
        this.getPostCommentUseCase = getPostCommentUseCase;
    }

    public void setView(@NonNull PostDetailView postDetailView) {
        this.postDetailView = postDetailView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.postDetailView = null;
    }

    public void initialize(Long postId) {
        this.loadPostDetail(postId);
    }

    public void refreshComment(Long postId) {
        this.postDetailView.showSwipeUpLoading();
        this.getPostCommentUseCase.execute(this.postDetailView.bindToLifecycle(),
                new Subscriber<List<Comment>>() {
                    @Override
                    public void onCompleted() {
                        PostDetailPresenter.this.postDetailView.hideSwipeUpLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        PostDetailPresenter.this.postDetailView.hideSwipeUpLoading();
                        PostDetailPresenter.this.postDetailView.showError("");
                    }

                    @Override
                    public void onNext(List<Comment> comments) {
                    }
                }, postId);
    }

    /**
     * Loads user details.
     */
    private void loadPostDetail(Long postId) {
        this.postDetailView.showLoading();
        this.getPostDetailUseCase.execute(this.postDetailView.bindToLifecycle(),
                new Subscriber<Post>() {
                    @Override
                    public void onCompleted() {
                        PostDetailPresenter.this.postDetailView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //do something
                        logger.error("", e);
                    }

                    @Override
                    public void onNext(Post post) {
                        PostDetailPresenter.this.postDetailView.renderPostDetail(post);
                    }
                }, postId);
    }
}

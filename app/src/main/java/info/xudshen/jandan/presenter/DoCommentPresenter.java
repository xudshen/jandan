package info.xudshen.jandan.presenter;

import android.support.annotation.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import info.xudshen.jandan.domain.interactor.UseCase;
import info.xudshen.jandan.view.ActionView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by xudshen on 16/2/24.
 */
public class DoCommentPresenter implements Presenter {
    private static final Logger logger = LoggerFactory.getLogger(DoCommentPresenter.class);
    private final UseCase doPostCommentUseCase;
    private final UseCase postDuoshuoCommentUseCase;
    private ActionView dataDetailView;

    @Inject
    public DoCommentPresenter(@Named("doPostComment") UseCase doPostCommentUseCase,
                              @Named("postDuoshuoComment") UseCase postDuoshuoCommentUseCase) {
        this.doPostCommentUseCase = doPostCommentUseCase;
        this.postDuoshuoCommentUseCase = postDuoshuoCommentUseCase;
    }

    public void setView(@NonNull ActionView dataDetailView) {
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

    public void doPostComment(Long postId, String name, String email, String content) {
        DoCommentPresenter.this.dataDetailView.showLoading();
        this.doPostCommentUseCase.execute(this.dataDetailView.bindToLifecycle(),
                new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(aLong -> DoCommentPresenter.this.dataDetailView.hideLoading());
                        DoCommentPresenter.this.dataDetailView.showError("error");
                    }

                    @Override
                    public void onNext(Boolean success) {
                        Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(aLong -> DoCommentPresenter.this.dataDetailView.hideLoading());
                        if (success) {
                            DoCommentPresenter.this.dataDetailView.showSuccess();
                        } else {
                            DoCommentPresenter.this.dataDetailView.showError("error");
                        }
                    }
                }, postId, name, email, content);
    }

    public void postDuoshuoComment(String threadKey, String authorName, String authorEmail,
                                   String message, String parentId) {
        DoCommentPresenter.this.dataDetailView.showLoading();
        this.postDuoshuoCommentUseCase.execute(this.dataDetailView.bindToLifecycle(),
                new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(aLong -> DoCommentPresenter.this.dataDetailView.hideLoading());
                        DoCommentPresenter.this.dataDetailView.showError("error");
                    }

                    @Override
                    public void onNext(Boolean success) {
                        Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(aLong -> DoCommentPresenter.this.dataDetailView.hideLoading());
                        if (success) {
                            DoCommentPresenter.this.dataDetailView.showSuccess();
                        } else {
                            DoCommentPresenter.this.dataDetailView.showError("error");
                        }
                    }
                }, threadKey, authorName, authorEmail, message, parentId);
    }
}

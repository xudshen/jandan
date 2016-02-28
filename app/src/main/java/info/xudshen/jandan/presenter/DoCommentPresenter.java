package info.xudshen.jandan.presenter;

import android.support.annotation.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import info.xudshen.jandan.domain.enums.ReaderItemType;
import info.xudshen.jandan.domain.interactor.UseCase;
import info.xudshen.jandan.domain.model.FavoItem;
import info.xudshen.jandan.utils.RetrofitErrorHelper;
import info.xudshen.jandan.view.ActionView;
import info.xudshen.jandan.view.DeleteDataView;
import info.xudshen.jandan.view.SaveDataView;
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
    private final UseCase saveFavoItemUseCase;
    private final UseCase deleteFavoItemUseCase;
    private ActionView doPostCommentView;
    private SaveDataView saveDataView;
    private DeleteDataView deleteDataView;

    @Inject
    public DoCommentPresenter(@Named("doPostComment") UseCase doPostCommentUseCase,
                              @Named("postDuoshuoComment") UseCase postDuoshuoCommentUseCase,
                              @Named("saveFavoItem") UseCase saveFavoItemUseCase,
                              @Named("deleteFavoItem") UseCase deleteFavoItemUseCase) {
        this.doPostCommentUseCase = doPostCommentUseCase;
        this.postDuoshuoCommentUseCase = postDuoshuoCommentUseCase;
        this.saveFavoItemUseCase = saveFavoItemUseCase;
        this.deleteFavoItemUseCase = deleteFavoItemUseCase;
    }

    public void setView(@NonNull ActionView doPostCommentView) {
        this.doPostCommentView = doPostCommentView;
    }

    public void setSaveDataView(@NonNull SaveDataView saveDataView) {
        if (this.saveDataView == null)
            this.saveDataView = saveDataView;
    }

    public void setDeleteDataView(@NonNull DeleteDataView deleteDataView) {
        if (this.deleteDataView == null)
            this.deleteDataView = deleteDataView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.doPostCommentView = null;
        this.saveDataView = null;
        this.deleteDataView = null;
    }

    public void doPostComment(Long postId, String name, String email, String content) {
        DoCommentPresenter.this.doPostCommentView.showLoading();
        this.doPostCommentUseCase.execute(this.doPostCommentView.bindToLifecycle(),
                new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(aLong -> DoCommentPresenter.this.doPostCommentView.hideLoading());
                        DoCommentPresenter.this.doPostCommentView.showError(RetrofitErrorHelper.transException(
                                DoCommentPresenter.this.doPostCommentView.context(), e));
                    }

                    @Override
                    public void onNext(Boolean success) {
                        Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(aLong -> DoCommentPresenter.this.doPostCommentView.hideLoading());
                        if (success) {
                            DoCommentPresenter.this.doPostCommentView.showSuccess();
                        } else {
                            DoCommentPresenter.this.doPostCommentView.showError(
                                    DoCommentPresenter.this.doPostCommentView.context()
                                            .getString(info.xudshen.jandan.R.string.comment_failed));
                        }
                    }
                }, postId, name, email, content);
    }

    public void postDuoshuoComment(String threadKey, String authorName, String authorEmail,
                                   String message, String parentId) {
        DoCommentPresenter.this.doPostCommentView.showLoading();
        this.postDuoshuoCommentUseCase.execute(this.doPostCommentView.bindToLifecycle(),
                new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(aLong -> DoCommentPresenter.this.doPostCommentView.hideLoading());
                        DoCommentPresenter.this.doPostCommentView.showError(RetrofitErrorHelper.transException(
                                DoCommentPresenter.this.doPostCommentView.context(), e));
                    }

                    @Override
                    public void onNext(Boolean success) {
                        Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(aLong -> DoCommentPresenter.this.doPostCommentView.hideLoading());
                        if (success) {
                            DoCommentPresenter.this.doPostCommentView.showSuccess();
                        } else {
                            DoCommentPresenter.this.doPostCommentView.showError(
                                    DoCommentPresenter.this.doPostCommentView.context()
                                            .getString(info.xudshen.jandan.R.string.comment_failed));
                        }
                    }
                }, threadKey, authorName, authorEmail, message, parentId);
    }

    public void saveFavoItem(FavoItem favoItem) {
        this.saveDataView.savingData();
        this.saveFavoItemUseCase.execute(this.saveDataView.bindToLifecycle(),
                new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        DoCommentPresenter.this.saveDataView.result(false);
                    }

                    @Override
                    public void onNext(Boolean o) {
                        DoCommentPresenter.this.saveDataView.result(o);
                    }
                }, favoItem);
    }

    public void deleteFavoItem(ReaderItemType type, String actualId) {
        this.deleteDataView.deletingData();
        this.deleteFavoItemUseCase.execute(this.deleteDataView.bindToLifecycle(),
                new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        DoCommentPresenter.this.deleteDataView.result(false);
                    }

                    @Override
                    public void onNext(Boolean o) {
                        DoCommentPresenter.this.deleteDataView.result(o);
                    }
                }, type, actualId);
    }
}

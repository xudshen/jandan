package info.xudshen.jandan.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

import info.xudshen.jandan.domain.enums.ReaderItemType;
import info.xudshen.jandan.domain.interactor.UseCase;
import info.xudshen.jandan.view.DeleteDataView;
import rx.Subscriber;

/**
 * Created by xudshen on 16/2/26.
 */
public class FavoListPresenter implements Presenter {
    private final UseCase deleteFavoItemUseCase;
    private DeleteDataView deleteDataView;

    @Inject
    public FavoListPresenter(@Named("deleteFavoItem") UseCase deleteFavoItemUseCase) {
        this.deleteFavoItemUseCase = deleteFavoItemUseCase;
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
        this.deleteDataView = null;
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
                        FavoListPresenter.this.deleteDataView.result(false);
                    }

                    @Override
                    public void onNext(Boolean o) {
                        FavoListPresenter.this.deleteDataView.result(o);
                    }
                }, type, actualId);
    }
}

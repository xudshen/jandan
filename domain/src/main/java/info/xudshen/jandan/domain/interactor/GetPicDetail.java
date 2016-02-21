package info.xudshen.jandan.domain.interactor;

import javax.inject.Inject;

import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.repository.PicRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/21.
 */
public class GetPicDetail extends UseCase {
    private final PicRepository picRepository;

    @Inject
    public GetPicDetail(PicRepository picRepository,
                        ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.picRepository = picRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        throw new UnsupportedOperationException("need pass a postId");
    }

    @Override
    protected Observable buildUseCaseObservable(Long... params) {
        Long picId = params[0];
        return this.picRepository.pic(picId);
    }
}

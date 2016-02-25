package info.xudshen.jandan.domain.interactor;

import javax.inject.Inject;

import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.repository.PostRepository;
import rx.Observable;

/**
 * Created by xudshen on 16/2/24.
 */
public class DoPostComment extends UseCase {
    private final PostRepository postRepository;

    @Inject
    public DoPostComment(PostRepository postRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.postRepository = postRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        throw new UnsupportedOperationException("need pass postId,name,email,content");
    }

    @Override
    protected Observable buildUseCaseObservable(Object... params) {
        Long postId = (Long) params[0];
        String name = (String) params[1];
        String email = (String) params[2];
        String content = (String) params[3];
        return this.postRepository.doPostComment(postId, name, email, content);
    }
}

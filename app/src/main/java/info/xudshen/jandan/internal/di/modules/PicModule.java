package info.xudshen.jandan.internal.di.modules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.interactor.GetPicList;
import info.xudshen.jandan.domain.interactor.IterableUseCase;
import info.xudshen.jandan.domain.repository.PicRepository;
import info.xudshen.jandan.internal.di.PerActivity;

/**
 * Created by xudshen on 16/2/20.
 */
@Module
public class PicModule {
    private static final Logger logger = LoggerFactory.getLogger(PicModule.class);

    public PicModule() {
    }

    @Provides
    @PerActivity
    @Named("picList")
    IterableUseCase provideGetPicListUseCase(PicRepository picRepository,
                                             ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetPicList(picRepository, threadExecutor, postExecutionThread);
    }

}

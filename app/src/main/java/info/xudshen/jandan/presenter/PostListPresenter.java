package info.xudshen.jandan.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import info.xudshen.jandan.data.dao.ArticleDao;
import info.xudshen.jandan.internal.di.PerActivity;
import info.xudshen.jandan.view.PostListView;

/**
 * Created by xudshen on 16/1/7.
 */
@PerActivity
public class PostListPresenter implements Presenter {
    private PostListView postListView;

    @Inject
    ArticleDao articleDao;

    @Inject
    public PostListPresenter() {
    }

    public void setView(@NonNull PostListView view) {
        this.postListView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
}

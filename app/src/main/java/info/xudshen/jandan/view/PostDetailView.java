package info.xudshen.jandan.view;

import info.xudshen.jandan.domain.model.Post;

/**
 * Created by xudshen on 16/2/16.
 */
public interface PostDetailView extends LoadDataView {
    void showSwipeUpLoading();

    void hideSwipeUpLoading();

    void renderPostDetail(Post post);
}

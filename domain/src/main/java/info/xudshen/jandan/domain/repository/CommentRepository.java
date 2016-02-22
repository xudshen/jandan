package info.xudshen.jandan.domain.repository;

import java.util.List;

import info.xudshen.jandan.domain.model.PicComment;
import info.xudshen.jandan.domain.model.PicItem;
import rx.Observable;

/**
 * Created by xudshen on 16/2/20.
 */
public interface CommentRepository {
    Observable<List<PicComment>> picCommentList(String threadKey);

    Observable<List<PicComment>> picCommentListNext(String threadKey);
}

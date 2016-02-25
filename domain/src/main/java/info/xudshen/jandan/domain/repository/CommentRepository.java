package info.xudshen.jandan.domain.repository;

import java.util.List;

import info.xudshen.jandan.domain.model.DuoshuoComment;
import rx.Observable;

/**
 * Created by xudshen on 16/2/20.
 */
public interface CommentRepository {
    Observable<List<DuoshuoComment>> commentList(String threadKey);

    Observable<List<DuoshuoComment>> commentListNext(String threadKey);

    Observable<Boolean> postDuoshuoComment(String threadKey,
                                           String authorName,
                                           String authorEmail,
                                           String message,
                                           String parentId);
}

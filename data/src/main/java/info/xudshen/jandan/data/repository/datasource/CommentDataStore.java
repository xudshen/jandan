package info.xudshen.jandan.data.repository.datasource;

import java.util.List;

import info.xudshen.jandan.domain.model.PicComment;
import rx.Observable;

/**
 * Created by xudshen on 16/2/22.
 */
public interface CommentDataStore {
    Observable<List<PicComment>> picCommentList(String threadKey);

    Observable<List<PicComment>> picCommentListNext(String threadKey);
}

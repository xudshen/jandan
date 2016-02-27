package info.xudshen.jandan.data.repository.datasource;

import java.util.List;

import info.xudshen.jandan.domain.enums.VoteResult;
import info.xudshen.jandan.domain.enums.VoteType;
import info.xudshen.jandan.domain.model.PicItem;
import rx.Observable;

/**
 * Created by xudshen on 16/2/20.
 */
public interface PicDataStore {
    Observable<PicItem> pic(Long picId);

    Observable<List<PicItem>> picList();

    Observable<List<PicItem>> picListNext();

    Observable<VoteResult> voteCommonItem(Long commentId, VoteType voteType);
}

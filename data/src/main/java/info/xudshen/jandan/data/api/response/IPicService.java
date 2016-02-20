package info.xudshen.jandan.data.api.response;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xudshen on 16/2/19.
 */
public interface IPicService {
    @GET("/?oxwlxojflwblxbsapi=jandan.get_pic_comments")
    Observable<PicListResponse> getPicListAsync(@Query("page") Long page);
}

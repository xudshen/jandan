package info.xudshen.jandan.data.api;

import info.xudshen.jandan.data.api.response.JokeListResponse;
import info.xudshen.jandan.data.api.response.PicListResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xudshen on 16/2/19.
 */
public interface ICommonItemService {
    @GET("/?oxwlxojflwblxbsapi=jandan.get_pic_comments")
    Observable<PicListResponse> getPicListAsync(@Query("page") Long page);

    @GET("/?oxwlxojflwblxbsapi=jandan.get_duan_comments")
    Observable<JokeListResponse> getJokeListAsync(@Query("page") Long page);
}

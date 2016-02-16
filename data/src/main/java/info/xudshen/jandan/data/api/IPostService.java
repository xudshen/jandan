package info.xudshen.jandan.data.api;

import info.xudshen.jandan.data.api.response.PostResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xudshen on 16/2/16.
 */
public interface IPostService {
    @GET("/?oxwlxojflwblxbsapi=get_post")
    Observable<PostResponse> getPostAsync(@Query("id") Long postId, @Query("include") String include);
}

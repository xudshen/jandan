package info.xudshen.jandan.data.api;

import info.xudshen.jandan.data.api.response.CommentCountResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xudshen on 16/2/22.
 */
public interface ICommentService {
    @GET("/api/threads/counts.json")
    Observable<CommentCountResponse> getPicCommentCount(@Query("threads") String threads);

    @GET("/api/threads/listPosts.json")
    Observable<Object> getPicCommentList(@Query("thread_key") String threadKey);
}

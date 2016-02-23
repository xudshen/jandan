package info.xudshen.jandan.data.api;

import info.xudshen.jandan.data.api.response.CommentCountResponse;
import info.xudshen.jandan.data.api.response.CommentListResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xudshen on 16/2/22.
 */
public interface ICommentService {
    @GET("/api/threads/counts.json")
    Observable<CommentCountResponse> getDuoshuoCommentList(@Query("threads") String threads);

    @GET("/api/threads/listPosts.json")
    Observable<CommentListResponse> getDuoshuoCommentList(@Query("thread_key") String threadKey,
                                                          @Query("page") Long page,
                                                          @Query("limit") Long limit);
}

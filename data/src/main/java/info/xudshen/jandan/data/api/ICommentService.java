package info.xudshen.jandan.data.api;

import info.xudshen.jandan.data.api.response.CommentCountResponse;
import info.xudshen.jandan.data.api.response.CommentListResponse;
import info.xudshen.jandan.data.api.response.CommonResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @FormUrlEncoded
    @POST("/api/posts/create.json")
    Observable<CommonResponse> postDuoshuoComment(@Field("thread_key") String threadKey,
                                                  @Field("author_name") String authorName,
                                                  @Field("author_email") String authorEmail,
                                                  @Field("message") String message,
                                                  @Field("parent_id") String parentId);
}

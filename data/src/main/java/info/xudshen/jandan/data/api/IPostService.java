package info.xudshen.jandan.data.api;

import info.xudshen.jandan.data.api.response.CommonResponse;
import info.xudshen.jandan.data.api.response.PostListResponse;
import info.xudshen.jandan.data.api.response.PostResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xudshen on 16/2/16.
 */
public interface IPostService {
    @GET("/?oxwlxojflwblxbsapi=get_post&include=url,title,content,excerpt,date,modified,comment_count,author,categories")
    Observable<PostResponse> getPostAsync(@Query("id") Long postId);

    @GET("/?oxwlxojflwblxbsapi=get_recent_posts&include=url,date,modified,author,title,excerpt,comment_count,categories,custom_fields&custom_fields=thumb_c,views&dev=1")
    Observable<PostListResponse> getPostListAsync(@Query("page") Long page);

    @GET("/?oxwlxojflwblxbsapi=get_post&include=comments")
    Observable<PostResponse> getCommentListAsync(@Query("id") Long postId);

    @POST("/?oxwlxojflwblxbsapi=respond.submit_comment")
    Observable<CommonResponse> postCommentAsync(@Query("post_id") Long postId,
                                                @Query("name") String name,
                                                @Query("email") String email,
                                                @Query("content") String content);

    @POST("/index.php?acv_ajax=true")
    @FormUrlEncoded
    Call<ResponseBody> voteComment(@Query("option") int option, @Field("ID") Long commentId);
}
package info.xudshen.jandan.data.api;

import info.xudshen.jandan.data.api.response.JokeListResponse;
import info.xudshen.jandan.data.api.response.PicListResponse;
import info.xudshen.jandan.data.api.response.VideoListResponse;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @GET("/?oxwlxojflwblxbsapi=jandan.get_video_comments")
    Observable<VideoListResponse> getVideoListAsync(@Query("page") Long page);

    @POST("/index.php?acv_ajax=true")
    @FormUrlEncoded
    Observable<ResponseBody> voteCommonItem(@Query("option") int option, @Field("ID") Long commentId);
}

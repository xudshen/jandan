package info.xudshen.jandan.data.api;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xudshen on 16/2/16.
 */
public class ApiAdapter {
    public static String BASE_URL = "http://i.jandan.net";
    public static String DUOSHUO_URL = "http://jandan.duoshuo.com";

    static <T> T createRetrofitService(final Class<T> clazz, final String baseUrl, Gson gson) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request = original.newBuilder()
                                .header("User-Agent", "Jandan Android App V3.0.0.2")
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    }
                })
                .addInterceptor(interceptor)
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        T service = retrofit.create(clazz);
        return service;
    }

    private static final IPostService POST_SERVICE = createRetrofitService(
            IPostService.class, BASE_URL, GsonSingleton.getInstance().getGson());
    private static final IPicService PIC_SERVICE = createRetrofitService(
            IPicService.class, BASE_URL, GsonSingleton.getInstance().getGson());
    private static final ICommentService COMMENT_SERVICE = createRetrofitService(
            ICommentService.class, DUOSHUO_URL, GsonSingleton.getInstance().getDuoshuoGson());

    public static IPostService getPostService() {
        return POST_SERVICE;
    }

    public static IPicService getPicService() {
        return PIC_SERVICE;
    }

    public static ICommentService getCommentService() {
        return COMMENT_SERVICE;
    }
}

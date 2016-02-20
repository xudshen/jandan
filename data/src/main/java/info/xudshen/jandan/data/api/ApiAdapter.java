package info.xudshen.jandan.data.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xudshen on 16/2/16.
 */
public class ApiAdapter {
    public static String BASE_URL = "http://i.jandan.net";

    static <T> T createRetrofitService(final Class<T> clazz, final String baseUrl) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(GsonSingleton.getInstance().getGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        T service = retrofit.create(clazz);
        return service;
    }

    private static final IPostService POST_SERVICE = createRetrofitService(IPostService.class, BASE_URL);
    private static final IPicService PIC_SERVICE = createRetrofitService(IPicService.class, BASE_URL);

    public static IPostService getPostService() {
        return POST_SERVICE;
    }

    public static IPicService getPicService() {
        return PIC_SERVICE;
    }
}

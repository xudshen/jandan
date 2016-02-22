package info.xudshen.jandan.data.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by xudshen on 16/2/16.
 */
public class GsonSingleton {
    private static GsonSingleton mInstance = null;
    private Gson gson;
    private Gson duoshuoGson;

    public GsonSingleton() {
        gson = new GsonBuilder().enableComplexMapKeySerialization()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        duoshuoGson = new GsonBuilder().enableComplexMapKeySerialization()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-ddTHH:mm:ssZ")
                .create();
    }

    public static GsonSingleton getInstance() {
        if (mInstance == null) {
            mInstance = new GsonSingleton();
        }
        return mInstance;
    }

    public Gson getGson() {
        return gson;
    }

    public Gson getDuoshuoGson() {
        return duoshuoGson;
    }
}

package info.xudshen.jandan.utils;

import android.content.Context;
import android.util.TypedValue;

import com.google.common.base.Splitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import jregex.Matcher;
import jregex.Pattern;

/**
 * Created by xudshen on 16/2/20.
 */
public class LayoutHelper {
    private static final Logger logger = LoggerFactory.getLogger(LayoutHelper.class);
    public static final Pattern ImageUrlPattern =
            new Pattern("({http}http|https):\\/\\/({host}\\S+?)\\/({size}\\S+?)\\/({id}\\S+?)\\.({type}\\S+)");

    public static String firstUrl(String urls) {
        List<String> urlList = Splitter.on(",").splitToList(urls);
        return urlList.size() > 0 ? urlList.get(0) : "";
    }

    public static String thumb(String url) {
        Matcher matcher = ImageUrlPattern.matcher(url);
        if (matcher.find()) {
            String http = matcher.group("http"),
                    host = matcher.group("host"),
                    size = matcher.group("size"),
                    id = matcher.group("id"),
                    type = matcher.group("type");
            if (type.equals("gif")) {
                String newUrl = String.format("%s://%s/%s/%s.%s", http, host, "thumbnail", id, type);
                return newUrl;
            } else return url;
        }
        return url;
    }


    public static int toPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}

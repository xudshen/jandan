package info.xudshen.jandan.utils;

import android.text.format.DateUtils;

import java.sql.Timestamp;

/**
 * Created by xudshen on 16/2/17.
 */
public class RealtimeTimeUtils {
    public static String realtime(Timestamp timestamp) {
        if (timestamp == null) return "";
        return DateUtils.getRelativeTimeSpanString(timestamp.getTime(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }
}

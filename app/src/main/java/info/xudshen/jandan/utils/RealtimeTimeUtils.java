package info.xudshen.jandan.utils;

import java.sql.Timestamp;

/**
 * Created by xudshen on 16/2/17.
 */
public class RealtimeTimeUtils {
    public static String realtime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toString() : "";
    }
}

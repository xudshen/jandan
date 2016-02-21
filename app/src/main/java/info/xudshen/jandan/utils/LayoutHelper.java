package info.xudshen.jandan.utils;

import android.content.Context;
import android.util.TypedValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xudshen on 16/2/20.
 */
public class LayoutHelper {
    private static final Logger logger = LoggerFactory.getLogger(LayoutHelper.class);

    public static int toPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}

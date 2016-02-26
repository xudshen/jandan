package info.xudshen.jandan.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by xudshen on 16/2/26.
 */
public class ClipboardHelper {
    public static void copy(Context context, String s) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(s, s);
        clipboard.setPrimaryClip(clip);
    }
}

package info.xudshen.jandan.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Patterns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jregex.Matcher;
import jregex.Pattern;

/**
 * Created by xudshen on 16/1/30.
 */
public class HtmlHelper {
    private static final Logger logger = LoggerFactory.getLogger(HtmlHelper.class);
    private static final Pattern NOT_EMPTY = new Pattern("(\\S+)");

    public static String formBody(String body) {
        body = body.replace("\\n", "");
        body = body.replace("\n", "");
        body = body.replace("<p><img", "<p class=\"p-with-image\"><img");
        return new StringBuffer("")
                .append("<html><head>")
                .append("<style>")
                .append("* {margin: 0; padding: 0;}")
                .append("body {font-size:15px; line-height: 1.42857143; color: #212121}")
                .append("p {padding-left: 16px;padding-right: 16px; margin: 0 0 10px}")
                .append("h4 {padding-left: 16px;padding-right: 16px; margin: 0 0 10px}")
                .append("em {color: #999}")
                .append(".p-with-image   {padding-left: 0px;padding-right: 0px}")
                .append("p > img    {width:100%;margin: 0 0 10px;}")
                .append("p > iframe {width:100%;}")
                .append(".share-links {display: none;}")
                .append("</style>")
                .append("</head><body>")
                .append(body)
                .append("</body></html>")
                .toString();
    }

    public static String commentCount(Long count) {
        return "{faw-comment}  " + count;
    }

    public static String voteOO(Long count) {
        return "OO  " + count;
    }

    public static String voteXX(Long count) {
        return "XX  " + count;
    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean isWholeBlank(final CharSequence cs) {
        if (cs == null) return true;
        Matcher matcher = NOT_EMPTY.matcher(cs.toString());
        return !matcher.find();
    }

    public static void openInBrowser(Activity activity, String uri) {
        if (Patterns.WEB_URL.matcher(uri).matches()) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            activity.startActivity(browserIntent);
        }
    }
}

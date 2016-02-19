package info.xudshen.jandan.data.utils;


import jregex.Matcher;
import jregex.Pattern;

/**
 * Created by xudshen on 16/2/19.
 */
public class HtmlUtils {
    private static final Pattern commentWrapperPattern = new Pattern("<p>({content}(\\s|\\S)+)<\\/p>");

    public static String cleanComment(String comment) {
        comment = comment.replace("\\n", "");
        comment = comment.replace("\n", "");
        Matcher matcher = commentWrapperPattern.matcher(comment);
        if (matcher.matches()) {
            comment = matcher.group("content");
        }
        return comment;
    }
}

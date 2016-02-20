package info.xudshen.jandan.data.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import jregex.Matcher;
import jregex.Pattern;

/**
 * Created by xudshen on 16/2/19.
 */
public class HtmlUtils {
    private static final Logger logger = LoggerFactory.getLogger(HtmlUtils.class);
    private static final Pattern commentWrapperPattern = new Pattern("<p>({content}(\\s|\\S)+?)<\\/p>");
    private static final Pattern commentToPattern = new Pattern("<a\\s+href=\"#comment-({commentTo}\\d+)\"(\\s|\\S)+<\\/a>");
    private static final Pattern picUrlPattern
            = new Pattern("<img\\s+src=\"({url}\\S+)\"(\\s|\\S)+?(<\\/img>|\\/>)");

    public static String cleanComment(String comment) {
        comment = comment.replace("\\n", "");
        comment = comment.replace("\n", "");
        StringBuffer newComment = new StringBuffer();
        Matcher matcher = commentWrapperPattern.matcher(comment);
        while (matcher.find()) {
            if (newComment.length() > 0) {
                newComment.append("<br/>");
            }
            newComment.append(matcher.group("content"));
        }
        return newComment.length() > 0 ? newComment.toString() : comment;
    }

    public static Long getCommentTo(String comment) {
        Matcher matcher = commentToPattern.matcher(comment);
        if (matcher.find()) {
            return Long.valueOf(matcher.group("commentTo"));
        }
        return null;
    }

    public static List<String> getPicUrlList(String picContent) {
        List<String> urlList = new ArrayList<>();
        Matcher matcher = picUrlPattern.matcher(picContent);
        while (matcher.find()) {
            urlList.add(matcher.group("url"));
        }
        return urlList;
    }
}

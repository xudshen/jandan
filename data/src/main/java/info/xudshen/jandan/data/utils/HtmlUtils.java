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

    public static final Pattern ImageUrlPattern =
            new Pattern("({http}http|https):\\/\\/({host}\\S+?)\\/({size}\\S+?)\\/({id}\\S+?)\\.({type}\\S+)");

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

    public static String fullSize(String url) {
        Matcher matcher = ImageUrlPattern.matcher(url);
        if (matcher.find()) {
            String http = matcher.group("http"),
                    host = matcher.group("host"),
                    size = matcher.group("size"),
                    id = matcher.group("id"),
                    type = matcher.group("type");
            if (type.equals("gif")) {
                String newUrl = String.format("%s://%s/%s/%s.%s", http, host, "bmiddle", id, type);
                return newUrl;
            } else return url;
        }
        return url;
    }
}

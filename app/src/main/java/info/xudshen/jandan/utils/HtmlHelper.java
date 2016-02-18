package info.xudshen.jandan.utils;

/**
 * Created by xudshen on 16/1/30.
 */
public class HtmlHelper {
    public static String formBody(String body) {
        body = body.replace("\\n", "");
        body = body.replace("<p><img", "<p class=\"p-with-image\"><img");
        return new StringBuffer("")
                .append("<html><head>")
                .append("<style>")
                .append("* {margin: 0; padding: 0;}")
                .append("body {font-size:15px; line-height: 1.42857143; color: #212121}")
                .append("p {padding-left: 16px;padding-right: 16px; margin: 0 0 10px}")
                .append("h4 {padding-left: 16px;padding-right: 16px; margin: 0 0 10px}")
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
}

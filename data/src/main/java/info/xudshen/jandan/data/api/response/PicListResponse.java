package info.xudshen.jandan.data.api.response;

import com.google.common.base.Joiner;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import info.xudshen.jandan.data.utils.HtmlUtils;
import info.xudshen.jandan.domain.model.PicItem;
import jregex.Matcher;
import jregex.Pattern;

/**
 * Created by xudshen on 16/2/20.
 */
public class PicListResponse {

    @Expose
    private String status;
    @Expose
    @SerializedName("comments")
    private List<PicItemWrapper> list;

    public PicListResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PicItemWrapper> getList() {
        return list;
    }

    public void setList(List<PicItemWrapper> list) {
        this.list = list;
    }

    public class PicItemWrapper extends PicItem {
        public PicItemWrapper() {
        }

        public PicItem getPicItem() {
            List<String> urlList = HtmlUtils.getPicUrlList(getContent());
            this.setPics(Joiner.on(",").skipNulls().join(urlList));
            this.setPicCount(Long.valueOf(urlList.size()));
            this.setPicFirst(HtmlUtils.firstThumb(urlList.get(0)));

            this.setCommentCount(0l);

            this.setHasGif(false);
            for (String url : urlList) {
                if (url.endsWith(".gif")) {
                    this.setHasGif(true);
                    break;
                }
            }
            return this;
        }
    }
}

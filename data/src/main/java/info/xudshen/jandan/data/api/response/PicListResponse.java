package info.xudshen.jandan.data.api.response;

import com.google.common.base.Joiner;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import info.xudshen.jandan.domain.model.PicItem;

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
        @Expose
        @SerializedName("pics")
        private List<String> picList;

        public PicItemWrapper() {
        }

        public List<String> getPicList() {
            return picList;
        }

        public void setPicList(List<String> picList) {
            this.picList = picList;
        }

        public PicItem getPicItem() {
            this.setPics(Joiner.on(",").skipNulls().join(picList));
            return this;
        }
    }
}

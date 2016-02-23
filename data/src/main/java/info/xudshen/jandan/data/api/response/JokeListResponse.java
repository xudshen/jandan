package info.xudshen.jandan.data.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import info.xudshen.jandan.domain.model.JokeItem;

/**
 * Created by xudshen on 16/2/20.
 */
public class JokeListResponse {
    @Expose
    private String status;
    @Expose
    @SerializedName("comments")
    private List<JokeItemWrapper> list;

    public JokeListResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<JokeItemWrapper> getList() {
        return list;
    }

    public void setList(List<JokeItemWrapper> list) {
        this.list = list;
    }

    public class JokeItemWrapper extends JokeItem {
        public JokeItemWrapper() {
        }

        public JokeItem getJokeItem() {
            return this;
        }
    }
}

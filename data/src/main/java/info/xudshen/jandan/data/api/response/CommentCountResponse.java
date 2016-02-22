package info.xudshen.jandan.data.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import info.xudshen.jandan.data.constants.Constants;

/**
 * Created by xudshen on 16/2/22.
 */
public class CommentCountResponse {
    @Expose
    private HashMap<String, CommentCount> response;

    public CommentCountResponse() {
    }

    public HashMap<String, CommentCount> getResponse() {
        return response;
    }

    public void setResponse(HashMap<String, CommentCount> response) {
        this.response = response;
    }

    public class CommentCount {
        @Expose
        String threadId;
        @Expose
        @SerializedName("comments")
        Long count;
        @Expose
        String threadKey;

        public CommentCount() {
        }

        public String getThreadId() {
            return threadId;
        }

        public void setThreadId(String threadId) {
            this.threadId = threadId;
        }

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }

        public String getThreadKey() {
            return threadKey;
        }

        public void setThreadKey(String threadKey) {
            this.threadKey = threadKey;
        }

        public Long getPicId() {
            String id = this.threadKey.substring(Constants.THREAD_PREFIX.length(), this.threadKey.length());
            return Long.valueOf(id);
        }
    }
}

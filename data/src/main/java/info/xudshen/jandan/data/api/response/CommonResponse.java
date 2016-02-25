package info.xudshen.jandan.data.api.response;

import com.google.gson.annotations.Expose;

/**
 * Created by xudshen on 16/2/24.
 */
public class CommonResponse {
    @Expose
    private String status;
    @Expose
    private Integer code;

    public CommonResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isOk() {
        return status != null && status.equals("ok");
    }

    public boolean isCode0() {
        return code == 0;
    }
}

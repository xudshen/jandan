package info.xudshen.jandan.data.api.response;

import com.google.gson.annotations.Expose;

import info.xudshen.jandan.domain.model.Post;

/**
 * Created by xudshen on 16/2/16.
 */
public class PostResponse {
    @Expose
    private String status;
    @Expose
    private Post post;
    @Expose
    private String previousUrl;
    @Expose
    private String nextUrl;

    public PostResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getPreviousUrl() {
        return previousUrl;
    }

    public void setPreviousUrl(String previousUrl) {
        this.previousUrl = previousUrl;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }
}

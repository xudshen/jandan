package info.xudshen.jandan.data.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import info.xudshen.jandan.domain.model.Author;
import info.xudshen.jandan.domain.model.Comment;
import info.xudshen.jandan.domain.model.Post;

/**
 * Created by xudshen on 16/2/16.
 */
public class PostResponse {
    @Expose
    private String status;
    @Expose
    @SerializedName("post")
    private PostWrapper postWrapper;
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

    public PostWrapper getPostWrapper() {
        return postWrapper;
    }

    public void setPostWrapper(PostWrapper postWrapper) {
        this.postWrapper = postWrapper;
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

    public class PostWrapper extends Post {
        @Expose
        private Author author;
        @Expose
        private List<Comment> comments;

        public PostWrapper() {
        }

        public Author getAuthor() {
            return author;
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

        public List<Comment> getComments() {
            return comments;
        }

        public void setComments(List<Comment> comments) {
            this.comments = comments;
        }
    }
}

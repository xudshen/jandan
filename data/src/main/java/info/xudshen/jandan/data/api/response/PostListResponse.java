package info.xudshen.jandan.data.api.response;

import com.google.gson.annotations.Expose;

import java.util.HashMap;
import java.util.List;

import info.xudshen.jandan.domain.model.Author;
import info.xudshen.jandan.domain.model.Comment;
import info.xudshen.jandan.domain.model.Post;
import info.xudshen.jandan.domain.model.SimplePost;

/**
 * Created by xudshen on 16/2/17.
 */
public class PostListResponse {
    @Expose
    private String status;
    @Expose
    private List<PostResponse.PostWrapper> posts;
    @Expose
    private Long count;
    @Expose
    private Long countTotal;
    @Expose
    private Long pages;

    public PostListResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PostResponse.PostWrapper> getPosts() {
        return posts;
    }

    public void setPosts(List<PostResponse.PostWrapper> posts) {
        this.posts = posts;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(Long countTotal) {
        this.countTotal = countTotal;
    }

    public Long getPages() {
        return pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    public class PostWrapper extends SimplePost {
        @Expose
        private Author author;
        @Expose
        private List<Comment> comments;
        @Expose
        private HashMap<String, Object> customFields;

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

        public HashMap<String, Object> getCustomFields() {
            return customFields;
        }

        public void setCustomFields(HashMap<String, Object> customFields) {
            this.customFields = customFields;
        }
    }
}

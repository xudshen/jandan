package info.xudshen.jandan.data.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import info.xudshen.jandan.domain.model.DuoshuoComment;

/**
 * Created by xudshen on 16/2/22.
 */
public class CommentListResponse {
    @Expose
    private Cursor cursor;

    @Expose
    @SerializedName("parentPosts")
    private HashMap<String, DuoshuoCommentWrapper> parentPosts;

    public CommentListResponse() {
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public HashMap<String, DuoshuoCommentWrapper> getParentPosts() {
        return parentPosts;
    }

    public void setParentPosts(HashMap<String, DuoshuoCommentWrapper> parentPosts) {
        this.parentPosts = parentPosts;
    }

    public class Cursor {
        @Expose
        private Long total;
        @Expose
        private Long pages;

        public Cursor() {
        }

        public Long getTotal() {
            return total;
        }

        public void setTotal(Long total) {
            this.total = total;
        }

        public Cursor(Long total, Long pages) {
            this.total = total;
            this.pages = pages;
        }
    }

    public class Author {
        @Expose
        private String userId;
        @Expose
        private String name;
        @Expose
        private String url;
        @Expose
        private String avatarUrl;

        public Author() {
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }
    }

    public class DuoshuoCommentWrapper extends DuoshuoComment {
        @Expose
        private Author author;

        public DuoshuoCommentWrapper() {
        }

        public Author getAuthor() {
            return author;
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

        public DuoshuoComment getDuoshuoComment() {
            if (author != null) {
                this.setAuthorId(author.getUserId());
                this.setAuthorName(author.getName());
                this.setAuthorUrl(author.getUrl());
                this.setAuthorAvatar(author.getAvatarUrl());
            }
            return this;
        }
    }
}
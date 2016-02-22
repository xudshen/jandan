package info.xudshen.jandan.data.api.response;

import com.google.gson.annotations.Expose;

import java.util.HashMap;

import info.xudshen.jandan.domain.model.PicComment;

/**
 * Created by xudshen on 16/2/22.
 */
public class CommentListResponse {
    @Expose
    private Cursor cursor;

    @Expose
    private HashMap<String, PicCommentWrapper> parentPosts;

    public CommentListResponse() {
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public HashMap<String, PicCommentWrapper> getParentPosts() {
        return parentPosts;
    }

    public void setParentPosts(HashMap<String, PicCommentWrapper> parentPosts) {
        this.parentPosts = parentPosts;
    }

    public class Cursor {
        @Expose
        private Long total;
        @Expose
        private Long cursor;

        public Cursor() {
        }

        public Long getTotal() {
            return total;
        }

        public void setTotal(Long total) {
            this.total = total;
        }

        public Long getCursor() {
            return cursor;
        }

        public void setCursor(Long cursor) {
            this.cursor = cursor;
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

    public class PicCommentWrapper extends PicComment {
        @Expose
        private Author author;

        public PicCommentWrapper() {
        }

        public Author getAuthor() {
            return author;
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

        public PicComment getPicComment() {
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

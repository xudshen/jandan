package info.xudshen.jandan.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;
import android.databinding.Bindable;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "POST".
 */
public class Post {

    private Long id;
    @Expose
    @SerializedName("id")
    private Long postId;
    @Expose
    private String url;
    @Expose
    private String title;
    @Expose
    private String content;
    @Expose
    private String excerpt;
    @Expose
    private Timestamp date;
    @Expose
    private Timestamp modified;
    @Expose
    private Long commentCount;
    @Expose
    private Long authorId;
    @Expose
    private String authorName;
    @Expose
    private Long categoryId;
    @Expose
    private String categoryDescription;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Post() {
    }

    public Post(Long id) {
        this.id = id;
    }

    public Post(Long id, Long postId, String url, String title, String content, String excerpt, Timestamp date, Timestamp modified, Long commentCount, Long authorId, String authorName, Long categoryId, String categoryDescription) {
        this.id = id;
        this.postId = postId;
        this.url = url;
        this.title = title;
        this.content = content;
        this.excerpt = excerpt;
        this.date = date;
        this.modified = modified;
        this.commentCount = commentCount;
        this.authorId = authorId;
        this.authorName = authorName;
        this.categoryId = categoryId;
        this.categoryDescription = categoryDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Timestamp getModified() {
        return modified;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}

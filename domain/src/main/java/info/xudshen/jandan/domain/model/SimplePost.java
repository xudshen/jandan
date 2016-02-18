package info.xudshen.jandan.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;
import android.databinding.Bindable;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "SIMPLE_POST".
 */
public class SimplePost {

    private Long id;
    @Expose
    @SerializedName("id")
    private Long postId;
    @Expose
    private String url;
    @Expose
    private String title;
    @Expose
    private String excerpt;
    @Expose
    private String thumbC;
    @Expose
    private Timestamp date;
    @Expose
    private Timestamp modified;
    @Expose
    private Long commentCount;
    @Expose
    private String authorName;
    @Expose
    private String categoryDescription;
    @Expose
    private String expireTag;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public SimplePost() {
    }

    public SimplePost(Long id) {
        this.id = id;
    }

    public SimplePost(Long id, Long postId, String url, String title, String excerpt, String thumbC, Timestamp date, Timestamp modified, Long commentCount, String authorName, String categoryDescription, String expireTag) {
        this.id = id;
        this.postId = postId;
        this.url = url;
        this.title = title;
        this.excerpt = excerpt;
        this.thumbC = thumbC;
        this.date = date;
        this.modified = modified;
        this.commentCount = commentCount;
        this.authorName = authorName;
        this.categoryDescription = categoryDescription;
        this.expireTag = expireTag;
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

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getThumbC() {
        return thumbC;
    }

    public void setThumbC(String thumbC) {
        this.thumbC = thumbC;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getExpireTag() {
        return expireTag;
    }

    public void setExpireTag(String expireTag) {
        this.expireTag = expireTag;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}

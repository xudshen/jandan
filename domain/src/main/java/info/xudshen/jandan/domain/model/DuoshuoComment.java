package info.xudshen.jandan.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;
import android.databinding.Bindable;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "DUOSHUO_COMMENT".
 */
public class DuoshuoComment {

    private Long id;
    @Expose
    @SerializedName("post_id")
    private String commentId;
    @Expose
    @SerializedName("thread_id")
    private String threadId;
    @Expose
    private String threadKey;
    @Expose
    private String message;
    @Expose
    @SerializedName("created_at")
    private Timestamp date;
    @Expose
    @SerializedName("parent_id")
    private String parentCommentId;
    @Expose
    private String authorId;
    @Expose
    private String authorName;
    @Expose
    private String authorAvatar;
    @Expose
    private String authorUrl;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public DuoshuoComment() {
    }

    public DuoshuoComment(Long id) {
        this.id = id;
    }

    public DuoshuoComment(Long id, String commentId, String threadId, String threadKey, String message, Timestamp date, String parentCommentId, String authorId, String authorName, String authorAvatar, String authorUrl) {
        this.id = id;
        this.commentId = commentId;
        this.threadId = threadId;
        this.threadKey = threadKey;
        this.message = message;
        this.date = date;
        this.parentCommentId = parentCommentId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorAvatar = authorAvatar;
        this.authorUrl = authorUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getThreadKey() {
        return threadKey;
    }

    public void setThreadKey(String threadKey) {
        this.threadKey = threadKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
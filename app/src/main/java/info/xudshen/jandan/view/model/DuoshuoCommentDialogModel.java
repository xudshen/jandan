package info.xudshen.jandan.view.model;

import java.sql.Timestamp;

import info.xudshen.jandan.domain.model.Comment;
import info.xudshen.jandan.domain.model.DuoshuoComment;

/**
 * Created by xudshen on 16/2/24.
 */
public class DuoshuoCommentDialogModel {
    private String authorAvatar;
    private String authorName;
    private Timestamp date;
    private String message;

    private boolean hasPreviousComment = false;

    public DuoshuoCommentDialogModel() {
    }

    public DuoshuoCommentDialogModel(DuoshuoComment comment) {
        if (comment != null) {
            this.authorAvatar = comment.getAuthorAvatar();
            this.authorName = comment.getAuthorName();
            this.date = comment.getDate();
            this.message = comment.getMessage();
            this.date = comment.getDate();
            this.hasPreviousComment = true;
        }
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isHasPreviousComment() {
        return hasPreviousComment;
    }

    public void setHasPreviousComment(boolean hasPreviousComment) {
        this.hasPreviousComment = hasPreviousComment;
    }
}

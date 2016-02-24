package info.xudshen.jandan.view.model;

import java.sql.Timestamp;

import info.xudshen.jandan.domain.model.Comment;

/**
 * Created by xudshen on 16/2/24.
 */
public class CommentDialogModel {
    private String name;
    private Timestamp date;
    private Long votePositive;
    private Long voteNegative;
    private String content;

    private boolean hasPreviousComment = false;

    public CommentDialogModel() {
    }

    public CommentDialogModel(Comment comment) {
        if (comment != null) {
            this.name = comment.getName();
            this.date = comment.getDate();
            this.votePositive = comment.getVotePositive();
            this.voteNegative = comment.getVoteNegative();
            this.content = comment.getContent();
            this.hasPreviousComment = true;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Long getVotePositive() {
        return votePositive;
    }

    public void setVotePositive(Long votePositive) {
        this.votePositive = votePositive;
    }

    public Long getVoteNegative() {
        return voteNegative;
    }

    public void setVoteNegative(Long voteNegative) {
        this.voteNegative = voteNegative;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isHasPreviousComment() {
        return hasPreviousComment;
    }

    public void setHasPreviousComment(boolean hasPreviousComment) {
        this.hasPreviousComment = hasPreviousComment;
    }
}

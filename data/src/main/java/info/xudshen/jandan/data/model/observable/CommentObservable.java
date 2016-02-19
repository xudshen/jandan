package info.xudshen.jandan.data.model.observable;

import info.xudshen.data.BR;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import info.xudshen.droiddata.dao.IModelObservable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import android.databinding.Bindable;

import info.xudshen.jandan.domain.model.Comment;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * IModelObservable<"COMMENT">
 */
public class CommentObservable extends android.databinding.BaseObservable  implements IModelObservable<Comment> {

    private Long id;
    @Expose
    @SerializedName("id")
    private Long commentId;
    @Expose
    private Long postId;
    @Expose
    private String name;
    @Expose
    private String url;
    @Expose
    private Timestamp date;
    @Expose
    private String content;
    @Expose
    private Long parent;
    @Expose
    private Long votePositive;
    @Expose
    private Long voteNegative;
    @Expose
    private Long index;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public CommentObservable() {
    }

    public CommentObservable(Long id) {
        this.id = id;
    }

    public CommentObservable(Long id, Long commentId, Long postId, String name, String url, Timestamp date, String content, Long parent, Long votePositive, Long voteNegative, Long index) {
        this.id = id;
        this.commentId = commentId;
        this.postId = postId;
        this.name = name;
        this.url = url;
        this.date = date;
        this.content = content;
        this.parent = parent;
        this.votePositive = votePositive;
        this.voteNegative = voteNegative;
        this.index = index;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Bindable
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        if ((this.commentId == null && commentId != null)
                || (this.commentId != null && !this.commentId.equals(commentId))) {
            this.commentId = commentId;
            notifyPropertyChanged(BR.commentId);
        }
    }

    @Bindable
    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        if ((this.postId == null && postId != null)
                || (this.postId != null && !this.postId.equals(postId))) {
            this.postId = postId;
            notifyPropertyChanged(BR.postId);
        }
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if ((this.name == null && name != null)
                || (this.name != null && !this.name.equals(name))) {
            this.name = name;
            notifyPropertyChanged(BR.name);
        }
    }

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if ((this.url == null && url != null)
                || (this.url != null && !this.url.equals(url))) {
            this.url = url;
            notifyPropertyChanged(BR.url);
        }
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if ((this.content == null && content != null)
                || (this.content != null && !this.content.equals(content))) {
            this.content = content;
            notifyPropertyChanged(BR.content);
        }
    }

    @Bindable
    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        if ((this.parent == null && parent != null)
                || (this.parent != null && !this.parent.equals(parent))) {
            this.parent = parent;
            notifyPropertyChanged(BR.parent);
        }
    }

    @Bindable
    public Long getVotePositive() {
        return votePositive;
    }

    public void setVotePositive(Long votePositive) {
        if ((this.votePositive == null && votePositive != null)
                || (this.votePositive != null && !this.votePositive.equals(votePositive))) {
            this.votePositive = votePositive;
            notifyPropertyChanged(BR.votePositive);
        }
    }

    @Bindable
    public Long getVoteNegative() {
        return voteNegative;
    }

    public void setVoteNegative(Long voteNegative) {
        if ((this.voteNegative == null && voteNegative != null)
                || (this.voteNegative != null && !this.voteNegative.equals(voteNegative))) {
            this.voteNegative = voteNegative;
            notifyPropertyChanged(BR.voteNegative);
        }
    }

    @Bindable
    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        if ((this.index == null && index != null)
                || (this.index != null && !this.index.equals(index))) {
            this.index = index;
            notifyPropertyChanged(BR.index);
        }
    }


    /**
     * convert entity to entityObservable
     */
    public CommentObservable(Comment entity) {
        this.id = entity.getId();
        this.commentId = entity.getCommentId();
        this.postId = entity.getPostId();
        this.name = entity.getName();
        this.url = entity.getUrl();
        this.date = entity.getDate();
        this.content = entity.getContent();
        this.parent = entity.getParent();
        this.votePositive = entity.getVotePositive();
        this.voteNegative = entity.getVoteNegative();
        this.index = entity.getIndex();
    }

    @Override
    public Long getModelKey() {
        return this.getId();
    }

    @Override
    public void refresh(Comment entity) {
        List<Integer> propertyChanges = new ArrayList<>();
        this.id = entity.getId();
        if ((this.commentId == null && entity.getCommentId() != null)
                || (this.commentId != null && !this.commentId.equals(entity.getCommentId()))) {
            this.commentId = entity.getCommentId();
            propertyChanges.add(BR.commentId);
        }
        if ((this.postId == null && entity.getPostId() != null)
                || (this.postId != null && !this.postId.equals(entity.getPostId()))) {
            this.postId = entity.getPostId();
            propertyChanges.add(BR.postId);
        }
        if ((this.name == null && entity.getName() != null)
                || (this.name != null && !this.name.equals(entity.getName()))) {
            this.name = entity.getName();
            propertyChanges.add(BR.name);
        }
        if ((this.url == null && entity.getUrl() != null)
                || (this.url != null && !this.url.equals(entity.getUrl()))) {
            this.url = entity.getUrl();
            propertyChanges.add(BR.url);
        }
        this.date = entity.getDate();
        if ((this.content == null && entity.getContent() != null)
                || (this.content != null && !this.content.equals(entity.getContent()))) {
            this.content = entity.getContent();
            propertyChanges.add(BR.content);
        }
        if ((this.parent == null && entity.getParent() != null)
                || (this.parent != null && !this.parent.equals(entity.getParent()))) {
            this.parent = entity.getParent();
            propertyChanges.add(BR.parent);
        }
        if ((this.votePositive == null && entity.getVotePositive() != null)
                || (this.votePositive != null && !this.votePositive.equals(entity.getVotePositive()))) {
            this.votePositive = entity.getVotePositive();
            propertyChanges.add(BR.votePositive);
        }
        if ((this.voteNegative == null && entity.getVoteNegative() != null)
                || (this.voteNegative != null && !this.voteNegative.equals(entity.getVoteNegative()))) {
            this.voteNegative = entity.getVoteNegative();
            propertyChanges.add(BR.voteNegative);
        }
        if ((this.index == null && entity.getIndex() != null)
                || (this.index != null && !this.index.equals(entity.getIndex()))) {
            this.index = entity.getIndex();
            propertyChanges.add(BR.index);
        }

        if (propertyChanges.size() == 1) {
            notifyPropertyChanged(propertyChanges.get(0));
        } else if (propertyChanges.size() > 1) {
            notifyChange();
        }
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}

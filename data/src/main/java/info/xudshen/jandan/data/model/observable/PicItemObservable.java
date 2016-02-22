package info.xudshen.jandan.data.model.observable;

import info.xudshen.data.BR;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import info.xudshen.droiddata.dao.IModelObservable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import android.databinding.Bindable;

import info.xudshen.jandan.domain.model.PicItem;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * IModelObservable<"PIC_ITEM">
 */
public class PicItemObservable extends android.databinding.BaseObservable  implements IModelObservable<PicItem> {

    private Long id;
    @Expose
    @SerializedName("comment_ID")
    private Long picId;
    @Expose
    @SerializedName("comment_author")
    private String picAuthor;
    @Expose
    @SerializedName("comment_author_email")
    private String picAuthorEmail;
    @Expose
    @SerializedName("comment_author_url")
    private String picAuthorUrl;
    @Expose
    @SerializedName("comment_date")
    private Timestamp date;
    @Expose
    private Long votePositive;
    @Expose
    private Long voteNegative;
    @Expose
    private Long commentCount;
    @Expose
    private Long commentThreadId;
    @Expose
    @SerializedName("comment_content")
    private String picContent;
    @Expose
    @SerializedName("text_content")
    private String picTextContent;
    
    private String pics;
    @Expose
    private String picFirst;
    @Expose
    private Long picCount;
    @Expose
    private Boolean hasGif;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public PicItemObservable() {
    }

    public PicItemObservable(Long id) {
        this.id = id;
    }

    public PicItemObservable(Long id, Long picId, String picAuthor, String picAuthorEmail, String picAuthorUrl, Timestamp date, Long votePositive, Long voteNegative, Long commentCount, Long commentThreadId, String picContent, String picTextContent, String pics, String picFirst, Long picCount, Boolean hasGif) {
        this.id = id;
        this.picId = picId;
        this.picAuthor = picAuthor;
        this.picAuthorEmail = picAuthorEmail;
        this.picAuthorUrl = picAuthorUrl;
        this.date = date;
        this.votePositive = votePositive;
        this.voteNegative = voteNegative;
        this.commentCount = commentCount;
        this.commentThreadId = commentThreadId;
        this.picContent = picContent;
        this.picTextContent = picTextContent;
        this.pics = pics;
        this.picFirst = picFirst;
        this.picCount = picCount;
        this.hasGif = hasGif;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Bindable
    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long picId) {
        if ((this.picId == null && picId != null)
                || (this.picId != null && !this.picId.equals(picId))) {
            this.picId = picId;
            notifyPropertyChanged(BR.picId);
        }
    }

    @Bindable
    public String getPicAuthor() {
        return picAuthor;
    }

    public void setPicAuthor(String picAuthor) {
        if ((this.picAuthor == null && picAuthor != null)
                || (this.picAuthor != null && !this.picAuthor.equals(picAuthor))) {
            this.picAuthor = picAuthor;
            notifyPropertyChanged(BR.picAuthor);
        }
    }

    @Bindable
    public String getPicAuthorEmail() {
        return picAuthorEmail;
    }

    public void setPicAuthorEmail(String picAuthorEmail) {
        if ((this.picAuthorEmail == null && picAuthorEmail != null)
                || (this.picAuthorEmail != null && !this.picAuthorEmail.equals(picAuthorEmail))) {
            this.picAuthorEmail = picAuthorEmail;
            notifyPropertyChanged(BR.picAuthorEmail);
        }
    }

    @Bindable
    public String getPicAuthorUrl() {
        return picAuthorUrl;
    }

    public void setPicAuthorUrl(String picAuthorUrl) {
        if ((this.picAuthorUrl == null && picAuthorUrl != null)
                || (this.picAuthorUrl != null && !this.picAuthorUrl.equals(picAuthorUrl))) {
            this.picAuthorUrl = picAuthorUrl;
            notifyPropertyChanged(BR.picAuthorUrl);
        }
    }

    @Bindable
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        if ((this.date == null && date != null)
                || (this.date != null && !this.date.equals(date))) {
            this.date = date;
            notifyPropertyChanged(BR.date);
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
    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        if ((this.commentCount == null && commentCount != null)
                || (this.commentCount != null && !this.commentCount.equals(commentCount))) {
            this.commentCount = commentCount;
            notifyPropertyChanged(BR.commentCount);
        }
    }

    @Bindable
    public Long getCommentThreadId() {
        return commentThreadId;
    }

    public void setCommentThreadId(Long commentThreadId) {
        if ((this.commentThreadId == null && commentThreadId != null)
                || (this.commentThreadId != null && !this.commentThreadId.equals(commentThreadId))) {
            this.commentThreadId = commentThreadId;
            notifyPropertyChanged(BR.commentThreadId);
        }
    }

    @Bindable
    public String getPicContent() {
        return picContent;
    }

    public void setPicContent(String picContent) {
        if ((this.picContent == null && picContent != null)
                || (this.picContent != null && !this.picContent.equals(picContent))) {
            this.picContent = picContent;
            notifyPropertyChanged(BR.picContent);
        }
    }

    @Bindable
    public String getPicTextContent() {
        return picTextContent;
    }

    public void setPicTextContent(String picTextContent) {
        if ((this.picTextContent == null && picTextContent != null)
                || (this.picTextContent != null && !this.picTextContent.equals(picTextContent))) {
            this.picTextContent = picTextContent;
            notifyPropertyChanged(BR.picTextContent);
        }
    }

    @Bindable
    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        if ((this.pics == null && pics != null)
                || (this.pics != null && !this.pics.equals(pics))) {
            this.pics = pics;
            notifyPropertyChanged(BR.pics);
        }
    }

    @Bindable
    public String getPicFirst() {
        return picFirst;
    }

    public void setPicFirst(String picFirst) {
        if ((this.picFirst == null && picFirst != null)
                || (this.picFirst != null && !this.picFirst.equals(picFirst))) {
            this.picFirst = picFirst;
            notifyPropertyChanged(BR.picFirst);
        }
    }

    @Bindable
    public Long getPicCount() {
        return picCount;
    }

    public void setPicCount(Long picCount) {
        if ((this.picCount == null && picCount != null)
                || (this.picCount != null && !this.picCount.equals(picCount))) {
            this.picCount = picCount;
            notifyPropertyChanged(BR.picCount);
        }
    }

    @Bindable
    public Boolean getHasGif() {
        return hasGif;
    }

    public void setHasGif(Boolean hasGif) {
        if ((this.hasGif == null && hasGif != null)
                || (this.hasGif != null && !this.hasGif.equals(hasGif))) {
            this.hasGif = hasGif;
            notifyPropertyChanged(BR.hasGif);
        }
    }


    /**
     * convert entity to entityObservable
     */
    public PicItemObservable(PicItem entity) {
        this.id = entity.getId();
        this.picId = entity.getPicId();
        this.picAuthor = entity.getPicAuthor();
        this.picAuthorEmail = entity.getPicAuthorEmail();
        this.picAuthorUrl = entity.getPicAuthorUrl();
        this.date = entity.getDate();
        this.votePositive = entity.getVotePositive();
        this.voteNegative = entity.getVoteNegative();
        this.commentCount = entity.getCommentCount();
        this.commentThreadId = entity.getCommentThreadId();
        this.picContent = entity.getPicContent();
        this.picTextContent = entity.getPicTextContent();
        this.pics = entity.getPics();
        this.picFirst = entity.getPicFirst();
        this.picCount = entity.getPicCount();
        this.hasGif = entity.getHasGif();
    }

    @Override
    public Long getModelKey() {
        return this.getId();
    }

    @Override
    public void refresh(PicItem entity) {
        List<Integer> propertyChanges = new ArrayList<>();
        this.id = entity.getId();
        if ((this.picId == null && entity.getPicId() != null)
                || (this.picId != null && !this.picId.equals(entity.getPicId()))) {
            this.picId = entity.getPicId();
            propertyChanges.add(BR.picId);
        }
        if ((this.picAuthor == null && entity.getPicAuthor() != null)
                || (this.picAuthor != null && !this.picAuthor.equals(entity.getPicAuthor()))) {
            this.picAuthor = entity.getPicAuthor();
            propertyChanges.add(BR.picAuthor);
        }
        if ((this.picAuthorEmail == null && entity.getPicAuthorEmail() != null)
                || (this.picAuthorEmail != null && !this.picAuthorEmail.equals(entity.getPicAuthorEmail()))) {
            this.picAuthorEmail = entity.getPicAuthorEmail();
            propertyChanges.add(BR.picAuthorEmail);
        }
        if ((this.picAuthorUrl == null && entity.getPicAuthorUrl() != null)
                || (this.picAuthorUrl != null && !this.picAuthorUrl.equals(entity.getPicAuthorUrl()))) {
            this.picAuthorUrl = entity.getPicAuthorUrl();
            propertyChanges.add(BR.picAuthorUrl);
        }
        if ((this.date == null && entity.getDate() != null)
                || (this.date != null && !this.date.equals(entity.getDate()))) {
            this.date = entity.getDate();
            propertyChanges.add(BR.date);
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
        if ((this.commentCount == null && entity.getCommentCount() != null)
                || (this.commentCount != null && !this.commentCount.equals(entity.getCommentCount()))) {
            this.commentCount = entity.getCommentCount();
            propertyChanges.add(BR.commentCount);
        }
        if ((this.commentThreadId == null && entity.getCommentThreadId() != null)
                || (this.commentThreadId != null && !this.commentThreadId.equals(entity.getCommentThreadId()))) {
            this.commentThreadId = entity.getCommentThreadId();
            propertyChanges.add(BR.commentThreadId);
        }
        if ((this.picContent == null && entity.getPicContent() != null)
                || (this.picContent != null && !this.picContent.equals(entity.getPicContent()))) {
            this.picContent = entity.getPicContent();
            propertyChanges.add(BR.picContent);
        }
        if ((this.picTextContent == null && entity.getPicTextContent() != null)
                || (this.picTextContent != null && !this.picTextContent.equals(entity.getPicTextContent()))) {
            this.picTextContent = entity.getPicTextContent();
            propertyChanges.add(BR.picTextContent);
        }
        if ((this.pics == null && entity.getPics() != null)
                || (this.pics != null && !this.pics.equals(entity.getPics()))) {
            this.pics = entity.getPics();
            propertyChanges.add(BR.pics);
        }
        if ((this.picFirst == null && entity.getPicFirst() != null)
                || (this.picFirst != null && !this.picFirst.equals(entity.getPicFirst()))) {
            this.picFirst = entity.getPicFirst();
            propertyChanges.add(BR.picFirst);
        }
        if ((this.picCount == null && entity.getPicCount() != null)
                || (this.picCount != null && !this.picCount.equals(entity.getPicCount()))) {
            this.picCount = entity.getPicCount();
            propertyChanges.add(BR.picCount);
        }
        if ((this.hasGif == null && entity.getHasGif() != null)
                || (this.hasGif != null && !this.hasGif.equals(entity.getHasGif()))) {
            this.hasGif = entity.getHasGif();
            propertyChanges.add(BR.hasGif);
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

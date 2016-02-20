package info.xudshen.jandan.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;
import android.databinding.Bindable;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "PIC_ITEM".
 */
public class PicItem {

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
    @SerializedName("comment_content")
    private String picContent;
    @Expose
    @SerializedName("text_content")
    private String picTextContent;
    
    private String pics;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public PicItem() {
    }

    public PicItem(Long id) {
        this.id = id;
    }

    public PicItem(Long id, Long picId, String picAuthor, String picAuthorEmail, String picAuthorUrl, Timestamp date, Long votePositive, Long voteNegative, String picContent, String picTextContent, String pics) {
        this.id = id;
        this.picId = picId;
        this.picAuthor = picAuthor;
        this.picAuthorEmail = picAuthorEmail;
        this.picAuthorUrl = picAuthorUrl;
        this.date = date;
        this.votePositive = votePositive;
        this.voteNegative = voteNegative;
        this.picContent = picContent;
        this.picTextContent = picTextContent;
        this.pics = pics;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long picId) {
        this.picId = picId;
    }

    public String getPicAuthor() {
        return picAuthor;
    }

    public void setPicAuthor(String picAuthor) {
        this.picAuthor = picAuthor;
    }

    public String getPicAuthorEmail() {
        return picAuthorEmail;
    }

    public void setPicAuthorEmail(String picAuthorEmail) {
        this.picAuthorEmail = picAuthorEmail;
    }

    public String getPicAuthorUrl() {
        return picAuthorUrl;
    }

    public void setPicAuthorUrl(String picAuthorUrl) {
        this.picAuthorUrl = picAuthorUrl;
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

    public String getPicContent() {
        return picContent;
    }

    public void setPicContent(String picContent) {
        this.picContent = picContent;
    }

    public String getPicTextContent() {
        return picTextContent;
    }

    public void setPicTextContent(String picTextContent) {
        this.picTextContent = picTextContent;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}

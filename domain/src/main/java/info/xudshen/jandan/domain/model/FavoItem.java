package info.xudshen.jandan.domain.model;

import com.google.gson.annotations.Expose;
import info.xudshen.jandan.domain.enums.ReaderItemType;

import java.io.Serializable;
import java.sql.Timestamp;
import android.databinding.Bindable;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "FAVO_ITEM".
 */
public class FavoItem implements Serializable{

    private Long id;
    @Expose
    private ReaderItemType type;
    @Expose
    private String actualId;
    @Expose
    private Timestamp addDate;
    @Expose
    private String url;
    @Expose
    private String title;
    @Expose
    private String excerpt;
    @Expose
    private String thumbC;
    @Expose
    private Long otherId;
    @Expose
    private String author;
    @Expose
    private String authorEmail;
    @Expose
    private String authorUrl;
    @Expose
    private Timestamp date;
    @Expose
    private Long votePositive;
    @Expose
    private Long voteNegative;
    @Expose
    private Long commentCount;
    @Expose
    private String threadId;
    @Expose
    private String content;
    @Expose
    private String textContent;
    @Expose
    private String pics;
    @Expose
    private String picFirst;
    @Expose
    private Long picCount;
    @Expose
    private Boolean hasGif;
    @Expose
    private String videoThumbnail;
    @Expose
    private String videoTitle;
    @Expose
    private String videoDescription;
    @Expose
    private Float videoDuration;
    @Expose
    private String videoLink;
    @Expose
    private String videoPlayer;
    @Expose
    private String videoSource;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public FavoItem() {
    }

    public FavoItem(Long id) {
        this.id = id;
    }

    public FavoItem(Long id, ReaderItemType type, String actualId, Timestamp addDate, String url, String title, String excerpt, String thumbC, Long otherId, String author, String authorEmail, String authorUrl, Timestamp date, Long votePositive, Long voteNegative, Long commentCount, String threadId, String content, String textContent, String pics, String picFirst, Long picCount, Boolean hasGif, String videoThumbnail, String videoTitle, String videoDescription, Float videoDuration, String videoLink, String videoPlayer, String videoSource) {
        this.id = id;
        this.type = type;
        this.actualId = actualId;
        this.addDate = addDate;
        this.url = url;
        this.title = title;
        this.excerpt = excerpt;
        this.thumbC = thumbC;
        this.otherId = otherId;
        this.author = author;
        this.authorEmail = authorEmail;
        this.authorUrl = authorUrl;
        this.date = date;
        this.votePositive = votePositive;
        this.voteNegative = voteNegative;
        this.commentCount = commentCount;
        this.threadId = threadId;
        this.content = content;
        this.textContent = textContent;
        this.pics = pics;
        this.picFirst = picFirst;
        this.picCount = picCount;
        this.hasGif = hasGif;
        this.videoThumbnail = videoThumbnail;
        this.videoTitle = videoTitle;
        this.videoDescription = videoDescription;
        this.videoDuration = videoDuration;
        this.videoLink = videoLink;
        this.videoPlayer = videoPlayer;
        this.videoSource = videoSource;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReaderItemType getType() {
        return type;
    }

    public void setType(ReaderItemType type) {
        this.type = type;
    }

    public String getActualId() {
        return actualId;
    }

    public void setActualId(String actualId) {
        this.actualId = actualId;
    }

    public Timestamp getAddDate() {
        return addDate;
    }

    public void setAddDate(Timestamp addDate) {
        this.addDate = addDate;
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

    public Long getOtherId() {
        return otherId;
    }

    public void setOtherId(Long otherId) {
        this.otherId = otherId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
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

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getPicFirst() {
        return picFirst;
    }

    public void setPicFirst(String picFirst) {
        this.picFirst = picFirst;
    }

    public Long getPicCount() {
        return picCount;
    }

    public void setPicCount(Long picCount) {
        this.picCount = picCount;
    }

    public Boolean getHasGif() {
        return hasGif;
    }

    public void setHasGif(Boolean hasGif) {
        this.hasGif = hasGif;
    }

    public String getVideoThumbnail() {
        return videoThumbnail;
    }

    public void setVideoThumbnail(String videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    public Float getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(Float videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getVideoPlayer() {
        return videoPlayer;
    }

    public void setVideoPlayer(String videoPlayer) {
        this.videoPlayer = videoPlayer;
    }

    public String getVideoSource() {
        return videoSource;
    }

    public void setVideoSource(String videoSource) {
        this.videoSource = videoSource;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}

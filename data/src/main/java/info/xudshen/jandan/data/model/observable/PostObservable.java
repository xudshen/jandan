package info.xudshen.jandan.data.model.observable;

import info.xudshen.data.BR;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import info.xudshen.droiddata.dao.IModelObservable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import android.databinding.Bindable;

import info.xudshen.jandan.domain.model.Post;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * IModelObservable<"POST">
 */
public class PostObservable extends android.databinding.BaseObservable  implements IModelObservable<Post> {

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
    private String comment_count;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public PostObservable() {
    }

    public PostObservable(Long id) {
        this.id = id;
    }

    public PostObservable(Long id, Long postId, String url, String title, String content, String excerpt, Timestamp date, Timestamp modified, String comment_count) {
        this.id = id;
        this.postId = postId;
        this.url = url;
        this.title = title;
        this.content = content;
        this.excerpt = excerpt;
        this.date = date;
        this.modified = modified;
        this.comment_count = comment_count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if ((this.title == null && title != null)
                || (this.title != null && !this.title.equals(title))) {
            this.title = title;
            notifyPropertyChanged(BR.title);
        }
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
    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        if ((this.excerpt == null && excerpt != null)
                || (this.excerpt != null && !this.excerpt.equals(excerpt))) {
            this.excerpt = excerpt;
            notifyPropertyChanged(BR.excerpt);
        }
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

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }


    /**
     * convert entity to entityObservable
     */
    public PostObservable(Post entity) {
        this.id = entity.getId();
        this.postId = entity.getPostId();
        this.url = entity.getUrl();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.excerpt = entity.getExcerpt();
        this.date = entity.getDate();
        this.modified = entity.getModified();
        this.comment_count = entity.getComment_count();
    }

    @Override
    public Long getModelKey() {
        return this.getId();
    }

    @Override
    public void refresh(Post entity) {
        List<Integer> propertyChanges = new ArrayList<>();
        this.id = entity.getId();
        if ((this.postId == null && entity.getPostId() != null)
                || (this.postId != null && !this.postId.equals(entity.getPostId()))) {
            this.postId = entity.getPostId();
            propertyChanges.add(BR.postId);
        }
        if ((this.url == null && entity.getUrl() != null)
                || (this.url != null && !this.url.equals(entity.getUrl()))) {
            this.url = entity.getUrl();
            propertyChanges.add(BR.url);
        }
        if ((this.title == null && entity.getTitle() != null)
                || (this.title != null && !this.title.equals(entity.getTitle()))) {
            this.title = entity.getTitle();
            propertyChanges.add(BR.title);
        }
        if ((this.content == null && entity.getContent() != null)
                || (this.content != null && !this.content.equals(entity.getContent()))) {
            this.content = entity.getContent();
            propertyChanges.add(BR.content);
        }
        if ((this.excerpt == null && entity.getExcerpt() != null)
                || (this.excerpt != null && !this.excerpt.equals(entity.getExcerpt()))) {
            this.excerpt = entity.getExcerpt();
            propertyChanges.add(BR.excerpt);
        }
        this.date = entity.getDate();
        this.modified = entity.getModified();
        this.comment_count = entity.getComment_count();

        if (propertyChanges.size() == 1) {
            notifyPropertyChanged(propertyChanges.get(0));
        } else if (propertyChanges.size() > 1) {
            notifyChange();
        }
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}

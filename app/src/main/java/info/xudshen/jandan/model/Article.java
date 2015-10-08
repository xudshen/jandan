package info.xudshen.jandan.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import java.sql.Timestamp;

import info.xudshen.jandan.BR;

/**
 * Created by xudshen on 15/10/5.
 */
public class Article extends BaseObservable {
    private String title;
    private String author;
    private Timestamp time;
    private String content = "0";

    public Article() {
    }

    public Article(String title) {
        this.title = title;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
        notifyPropertyChanged(BR.author);
    }

    @Bindable
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
        notifyPropertyChanged(BR.time);
    }

    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyPropertyChanged(BR.content);
    }
}

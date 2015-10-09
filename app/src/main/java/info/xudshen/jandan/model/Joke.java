package info.xudshen.jandan.model;

import com.google.gson.annotations.Expose;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "JOKE".
 */
public class Joke {

    private Long id;
    @Expose
    private Long jokeId;
    @Expose
    private String author;
    @Expose
    private String content;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Joke() {
    }

    public Joke(Long id) {
        this.id = id;
    }

    public Joke(Long id, Long jokeId, String author, String content) {
        this.id = id;
        this.jokeId = jokeId;
        this.author = author;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJokeId() {
        return jokeId;
    }

    public void setJokeId(Long jokeId) {
        this.jokeId = jokeId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}

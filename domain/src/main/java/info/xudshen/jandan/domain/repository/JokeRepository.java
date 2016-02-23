package info.xudshen.jandan.domain.repository;

import java.util.List;

import info.xudshen.jandan.domain.model.JokeItem;
import rx.Observable;

/**
 * Created by xudshen on 16/2/20.
 */
public interface JokeRepository {
    Observable<JokeItem> joke(Long jokeId);

    Observable<List<JokeItem>> jokeList();

    Observable<List<JokeItem>> jokeListNextPage();
}

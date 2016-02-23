package info.xudshen.jandan.data.repository.datasource;

import java.util.List;

import info.xudshen.jandan.domain.model.JokeItem;
import rx.Observable;

/**
 * Created by xudshen on 16/2/20.
 */
public interface JokeDataStore {
    Observable<JokeItem> joke(Long jokeId);

    Observable<List<JokeItem>> jokeList();

    Observable<List<JokeItem>> jokeListNext();
}

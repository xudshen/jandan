package info.xudshen.jandan.domain.model;

import java.sql.Timestamp;

import info.xudshen.jandan.domain.enums.ReaderItemType;

/**
 * Created by xudshen on 16/2/25.
 */
public class FavoItemTrans {
    public static JokeItem toJokeItem(FavoItem favoItem) {
        JokeItem jokeItem = new JokeItem();
        jokeItem.setJokeId(favoItem.getOtherId());
        jokeItem.setAuthor(favoItem.getAuthor());
        jokeItem.setAuthorEmail(favoItem.getAuthorEmail());
        jokeItem.setAuthorUrl(favoItem.getAuthorUrl());
        jokeItem.setDate(favoItem.getDate());
        jokeItem.setVotePositive(favoItem.getVotePositive());
        jokeItem.setVoteNegative(favoItem.getVoteNegative());
        jokeItem.setCommentCount(favoItem.getCommentCount());
        jokeItem.setThreadId(favoItem.getThreadId());
        jokeItem.setContent(favoItem.getContent());
        jokeItem.setTextContent(favoItem.getTextContent());
        return jokeItem;
    }

    public static FavoItem fromJokeItem(JokeItem jokeItem) {
        FavoItem favoItem = new FavoItem();
        favoItem.setOtherId(jokeItem.getJokeId());
        favoItem.setAuthor(favoItem.getAuthor());
        favoItem.setAuthorEmail(favoItem.getAuthorEmail());
        favoItem.setAuthorUrl(favoItem.getAuthorUrl());
        favoItem.setDate(favoItem.getDate());
        favoItem.setVotePositive(favoItem.getVotePositive());
        favoItem.setVoteNegative(favoItem.getVoteNegative());
        favoItem.setCommentCount(favoItem.getCommentCount());
        favoItem.setThreadId(favoItem.getThreadId());
        favoItem.setContent(favoItem.getContent());
        favoItem.setTextContent(favoItem.getTextContent());

        favoItem.setType(ReaderItemType.SimpleJoke);
        favoItem.setActualId(jokeItem.getJokeId().toString());
        favoItem.setAddDate(new Timestamp(System.currentTimeMillis()));
        return favoItem;
    }
}

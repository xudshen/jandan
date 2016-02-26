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
        favoItem.setAuthor(jokeItem.getAuthor());
        favoItem.setAuthorEmail(jokeItem.getAuthorEmail());
        favoItem.setAuthorUrl(jokeItem.getAuthorUrl());
        favoItem.setDate(jokeItem.getDate());
        favoItem.setVotePositive(jokeItem.getVotePositive());
        favoItem.setVoteNegative(jokeItem.getVoteNegative());
        favoItem.setCommentCount(jokeItem.getCommentCount());
        favoItem.setThreadId(jokeItem.getThreadId());
        favoItem.setContent(jokeItem.getContent());
        favoItem.setTextContent(jokeItem.getTextContent());

        favoItem.setType(ReaderItemType.SimpleJoke);
        favoItem.setActualId(jokeItem.getJokeId().toString());
        favoItem.setAddDate(new Timestamp(System.currentTimeMillis()));
        return favoItem;
    }

    public static PicItem toPicItem(FavoItem favoItem) {
        PicItem picItem = new PicItem();
        picItem.setPicId(favoItem.getOtherId());
        picItem.setAuthor(favoItem.getAuthor());
        picItem.setAuthorEmail(favoItem.getAuthorEmail());
        picItem.setAuthorUrl(favoItem.getAuthorUrl());
        picItem.setDate(favoItem.getDate());
        picItem.setVotePositive(favoItem.getVotePositive());
        picItem.setVoteNegative(favoItem.getVoteNegative());
        picItem.setCommentCount(favoItem.getCommentCount());
        picItem.setThreadId(favoItem.getThreadId());
        picItem.setContent(favoItem.getContent());
        picItem.setTextContent(favoItem.getTextContent());

        picItem.setPics(favoItem.getPics());
        picItem.setPicFirst(favoItem.getPicFirst());
        picItem.setPicCount(favoItem.getPicCount());
        picItem.setHasGif(favoItem.getHasGif());

        return picItem;
    }

    public static FavoItem fromPicItem(PicItem picItem) {
        FavoItem favoItem = new FavoItem();
        favoItem.setOtherId(picItem.getPicId());
        favoItem.setAuthor(picItem.getAuthor());
        favoItem.setAuthorEmail(picItem.getAuthorEmail());
        favoItem.setAuthorUrl(picItem.getAuthorUrl());
        favoItem.setDate(picItem.getDate());
        favoItem.setVotePositive(picItem.getVotePositive());
        favoItem.setVoteNegative(picItem.getVoteNegative());
        favoItem.setCommentCount(picItem.getCommentCount());
        favoItem.setThreadId(picItem.getThreadId());
        favoItem.setContent(picItem.getContent());
        favoItem.setTextContent(picItem.getTextContent());

        favoItem.setPics(picItem.getPics());
        favoItem.setPicFirst(picItem.getPicFirst());
        favoItem.setPicCount(picItem.getPicCount());
        favoItem.setHasGif(picItem.getHasGif());

        favoItem.setType(ReaderItemType.SimplePic);
        favoItem.setActualId(picItem.getPicId().toString());
        favoItem.setAddDate(new Timestamp(System.currentTimeMillis()));
        return favoItem;
    }

    public static VideoItem toVideoItem(FavoItem favoItem) {
        VideoItem videoItem = new VideoItem();
        videoItem.setVideoId(favoItem.getOtherId());
        videoItem.setAuthor(favoItem.getAuthor());
        videoItem.setAuthorEmail(favoItem.getAuthorEmail());
        videoItem.setAuthorUrl(favoItem.getAuthorUrl());
        videoItem.setDate(favoItem.getDate());
        videoItem.setVotePositive(favoItem.getVotePositive());
        videoItem.setVoteNegative(favoItem.getVoteNegative());
        videoItem.setCommentCount(favoItem.getCommentCount());
        videoItem.setThreadId(favoItem.getThreadId());
        videoItem.setContent(favoItem.getContent());
        videoItem.setTextContent(favoItem.getTextContent());

        videoItem.setVideoThumbnail(favoItem.getVideoThumbnail());
        videoItem.setVideoTitle(favoItem.getVideoTitle());
        videoItem.setVideoDescription(favoItem.getVideoDescription());
        videoItem.setVideoDuration(favoItem.getVideoDuration());
        videoItem.setVideoLink(favoItem.getVideoLink());
        videoItem.setVideoPlayer(favoItem.getVideoPlayer());
        videoItem.setVideoSource(favoItem.getVideoSource());

        return videoItem;
    }

    public static FavoItem fromVideoItem(VideoItem videoItem) {
        FavoItem favoItem = new FavoItem();
        favoItem.setOtherId(videoItem.getVideoId());
        favoItem.setAuthor(videoItem.getAuthor());
        favoItem.setAuthorEmail(videoItem.getAuthorEmail());
        favoItem.setAuthorUrl(videoItem.getAuthorUrl());
        favoItem.setDate(videoItem.getDate());
        favoItem.setVotePositive(videoItem.getVotePositive());
        favoItem.setVoteNegative(videoItem.getVoteNegative());
        favoItem.setCommentCount(videoItem.getCommentCount());
        favoItem.setThreadId(videoItem.getThreadId());
        favoItem.setContent(videoItem.getContent());
        favoItem.setTextContent(videoItem.getTextContent());

        favoItem.setVideoThumbnail(videoItem.getVideoThumbnail());
        favoItem.setVideoTitle(videoItem.getVideoTitle());
        favoItem.setVideoDescription(videoItem.getVideoDescription());
        favoItem.setVideoDuration(videoItem.getVideoDuration());
        favoItem.setVideoLink(videoItem.getVideoLink());
        favoItem.setVideoPlayer(videoItem.getVideoPlayer());
        favoItem.setVideoSource(videoItem.getVideoSource());

        favoItem.setType(ReaderItemType.SimpleVideo);
        favoItem.setActualId(videoItem.getVideoId().toString());
        favoItem.setAddDate(new Timestamp(System.currentTimeMillis()));
        return favoItem;
    }

    public static SimplePost toSimplePost(FavoItem favoItem) {
        SimplePost simplePost = new SimplePost();
        simplePost.setPostId(favoItem.getOtherId());
        simplePost.setAuthorName(favoItem.getAuthor());

        simplePost.setUrl(favoItem.getUrl());
        simplePost.setTitle(favoItem.getTitle());
        simplePost.setExcerpt(favoItem.getExcerpt());
        simplePost.setThumbC(favoItem.getThumbC());

        simplePost.setDate(favoItem.getDate());
        simplePost.setCommentCount(favoItem.getCommentCount());

        return simplePost;
    }

    public static FavoItem fromSimplePost(SimplePost simplePost) {
        FavoItem favoItem = new FavoItem();
        favoItem.setOtherId(simplePost.getPostId());
        favoItem.setAuthor(simplePost.getAuthorName());

        favoItem.setUrl(simplePost.getUrl());
        favoItem.setTitle(simplePost.getTitle());
        favoItem.setExcerpt(simplePost.getExcerpt());
        favoItem.setThumbC(simplePost.getThumbC());

        favoItem.setDate(simplePost.getDate());
        favoItem.setCommentCount(simplePost.getCommentCount());

        favoItem.setType(ReaderItemType.SimplePost);
        favoItem.setActualId(simplePost.getPostId().toString());
        favoItem.setAddDate(new Timestamp(System.currentTimeMillis()));
        return favoItem;
    }

    public static <T> FavoItem from(T item) {
        if (SimplePost.class.isInstance(item)) {
            return fromSimplePost((SimplePost) item);
        } else if (JokeItem.class.isInstance(item)) {
            return fromJokeItem((JokeItem) item);
        } else if (PicItem.class.isInstance(item)) {
            return fromPicItem((PicItem) item);
        } else if (VideoItem.class.isInstance(item)) {
            return fromVideoItem((VideoItem) item);
        }
        return null;
    }

    public static <T> T to(FavoItem item) {
        switch (item.getType()) {
            case SimplePost: {
                return (T) toSimplePost(item);
            }
            case SimpleJoke: {
                return (T) toJokeItem(item);
            }
            case SimplePic: {
                return (T) toPicItem(item);
            }
            case SimpleVideo: {
                return (T) toVideoItem(item);
            }
        }
        return null;
    }
}

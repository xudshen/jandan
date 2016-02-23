package info.xudshen.jandan.data.api.response;

import com.google.common.base.Joiner;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import info.xudshen.jandan.data.utils.HtmlUtils;
import info.xudshen.jandan.domain.model.VideoItem;

/**
 * Created by xudshen on 16/2/20.
 */
public class VideoListResponse {

    @Expose
    private String status;
    @Expose
    @SerializedName("comments")
    private List<VideoItemWrapper> list;

    public VideoListResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<VideoItemWrapper> getList() {
        return list;
    }

    public void setList(List<VideoItemWrapper> list) {
        this.list = list;
    }

    public class Video {
        @Expose
        private String title;
        @Expose
        private String description;
        @Expose
        private String thumbnail;
        @Expose
        private Long duration;
        @Expose
        private String link;
        @Expose
        private String player;
        @Expose
        private String source;

        public Video() {
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public Long getDuration() {
            return duration;
        }

        public void setDuration(Long duration) {
            this.duration = duration;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getPlayer() {
            return player;
        }

        public void setPlayer(String player) {
            this.player = player;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }

    public class VideoItemWrapper extends VideoItem {
        @Expose
        private List<Video> videos;

        public List<Video> getVideos() {
            return videos;
        }

        public void setVideos(List<Video> videos) {
            this.videos = videos;
        }

        public VideoItemWrapper() {
        }

        public VideoItem getVideoItem() {
            if (videos != null && videos.size() > 0) {
                setVideoThumbnail(videos.get(0).thumbnail);
                setVideoTitle(videos.get(0).title);
                setVideoDescription(videos.get(0).description);
                setVideoDuration(videos.get(0).duration);
                setVideoLink(videos.get(0).link);
                setVideoPlayer(videos.get(0).player);
                setVideoSource(videos.get(0).source);
            }
            return this;
        }
    }
}

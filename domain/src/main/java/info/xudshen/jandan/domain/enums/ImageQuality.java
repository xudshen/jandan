package info.xudshen.jandan.domain.enums;

/**
 * Created by xudshen on 16/2/29.
 */
public enum ImageQuality {
    THUMBNAIL("thumbnail"),
    LOW("small"),
    MEDIUM("bmiddle"),
    HIGH("mw720");
    private String quality;

    ImageQuality(String quality) {
        this.quality = quality;
    }

    public String getQuality() {
        return quality;
    }
}

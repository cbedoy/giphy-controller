package cbedoy.giphycontroller.model;

/**
 * GiphyController
 * <p>
 * Created by bedoy on 10/1/17.
 */

public class ImagesMetadata {
    private String type;
    private String caption;
    private String id;
    private String url;
    private String slug;
    private ImagesSet images;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImagesSet getImages() {
        return images;
    }

    public void setImages(ImagesSet images) {
        this.images = images;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }


    public String getSlug() {
        return slug;
    }
}

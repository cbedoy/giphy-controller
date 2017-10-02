package cbedoy.giphycontroller;

/**
 * GiphyController
 * <p>
 * Created by bedoy on 10/2/17.
 */

public class CBGiphy
{
    private String baseURL;
    private String fixedUrl;
    private int height;
    private int width;
    private String id;

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public String getFixedUrl() {
        return fixedUrl;
    }

    public void setFixedUrl(String fixedUrl) {
        this.fixedUrl = fixedUrl;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

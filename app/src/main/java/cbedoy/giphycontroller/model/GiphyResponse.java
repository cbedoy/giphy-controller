package cbedoy.giphycontroller.model;

import java.util.List;

import cbedoy.giphycontroller.model.ImagesMetadata;

/**
 * GiphyController
 * <p>
 * Created by bedoy on 10/1/17.
 */

public class GiphyResponse {
    private List<ImagesMetadata> data;

    public List<ImagesMetadata> getData() {
        return data;
    }

    public void setData(List<ImagesMetadata> data) {
        this.data = data;
    }
}

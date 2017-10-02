package cbedoy.giphycontroller.model;


import com.google.gson.annotations.SerializedName;

import cbedoy.giphycontroller.model.GiphyImage;

/**
 * GiphyController
 * <p>
 * Created by bedoy on 10/1/17.
 */

public class ImagesSet {

    @SerializedName("fixed_height")
    private GiphyImage fixedHeight;

    public void setFixedHeight(GiphyImage fixedHeight) {
        this.fixedHeight = fixedHeight;
    }

    public GiphyImage getFixedHeight() {
        return fixedHeight;
    }
}
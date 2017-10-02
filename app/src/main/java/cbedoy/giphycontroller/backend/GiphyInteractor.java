package cbedoy.giphycontroller.backend;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cbedoy.giphycontroller.CBGiphy;
import cbedoy.giphycontroller.IGiphy;
import cbedoy.giphycontroller.model.GiphyImage;
import cbedoy.giphycontroller.model.GiphyResponse;
import cbedoy.giphycontroller.model.ImagesMetadata;
import cbedoy.giphycontroller.model.ImagesSet;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * GiphyController
 * <p>
 * Created by bedoy on 10/1/17.
 */

public class GiphyInteractor implements IGiphy.IGiphyInteractor {
    private IGiphy.IGiphyPresenter presenter;
    private IGiphy.IGiphyService service;

    public void setService(IGiphy.IGiphyService service) {
        this.service = service;
    }

    public void setPresenter(IGiphy.IGiphyPresenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void requestTreading() {
        Observable<GiphyResponse> observable = service.treading(100, "dc6zaTOxFJmzC");
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<GiphyResponse>() {
                    @Override
                    public void onNext(GiphyResponse response) {
                        Log.d(getClass().getCanonicalName(), "onNext");
                        processOnNextResponse(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(getClass().getCanonicalName(), "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(getClass().getCanonicalName(), "onComplete");
                    }
                });
    }

    private void processOnNextResponse(GiphyResponse response)
    {
        List<CBGiphy> list = new ArrayList<>();
        if (response != null){
            List<ImagesMetadata> data = response.getData();

            for (ImagesMetadata imagesMetadata : data){
                ImagesSet images = imagesMetadata.getImages();

                GiphyImage fixedHeight = images.getFixedHeight();

                int height = fixedHeight.getHeight();
                int width = fixedHeight.getWidth();
                String url = fixedHeight.getUrl();

                if (height > 0 && width > 0 && url != null && url.length() > 0) {

                    String id = imagesMetadata.getId();
                    String baseURL = imagesMetadata.getUrl();


                    String improvedURL = url;
                    String[] split = url.split("giphy.com");
                    String pathURL = split[0];

                    int lastIndexOf = pathURL.lastIndexOf("/");

                    if (lastIndexOf > 0) {
                        improvedURL = "https://i.giphy.com" + split[1];
                    }

                    CBGiphy cbGiphy = new CBGiphy();
                    cbGiphy.setFixedUrl(improvedURL);
                    cbGiphy.setHeight(height);
                    cbGiphy.setWidth(width);
                    cbGiphy.setId(id);
                    cbGiphy.setBaseURL(baseURL);

                    list.add(cbGiphy);
                }
            }

            presenter.loadedTreading(list);
        }
    }
}

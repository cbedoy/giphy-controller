package cbedoy.giphycontroller.backend;

import java.util.List;

import cbedoy.giphycontroller.CBGiphy;
import cbedoy.giphycontroller.IGiphy;

/**
 * GiphyController
 * <p>
 * Created by bedoy on 10/1/17.
 */

public class GiphyPresenter implements IGiphy.IGiphyPresenter
{
    private IGiphy.IGiphyViewController viewController;
    private IGiphy.IGiphyInteractor interactor;

    public void setInteractor(IGiphy.IGiphyInteractor interactor) {
        this.interactor = interactor;
    }

    public void setViewController(IGiphy.IGiphyViewController viewController) {
        this.viewController = viewController;
    }

    @Override
    public void loadTreading() {
        interactor.requestTreading();
    }

    @Override
    public void loadedTreading(List<CBGiphy> data) {
        viewController.onLoadingTreading(data);
    }
}

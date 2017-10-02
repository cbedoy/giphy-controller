package cbedoy.giphycontroller;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cbedoy.giphycontroller.backend.GiphyInteractor;
import cbedoy.giphycontroller.backend.GiphyPresenter;
import cbedoy.giphycontroller.backend.GiphyViewController;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GiphyViewController giphyViewController = new GiphyViewController();
        GiphyInteractor giphyInteractor = new GiphyInteractor();
        GiphyPresenter giphyPresenter = new GiphyPresenter();

        giphyInteractor.setService(ServiceGenerator.createService(IGiphy.IGiphyService.class));
        giphyInteractor.setPresenter(giphyPresenter);
        giphyPresenter.setInteractor(giphyInteractor);
        giphyPresenter.setViewController(giphyViewController);
        giphyViewController.setPresenter(giphyPresenter);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.main_container, giphyViewController);
        transaction.commit();
    }
}

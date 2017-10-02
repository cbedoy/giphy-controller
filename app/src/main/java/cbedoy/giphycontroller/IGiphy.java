package cbedoy.giphycontroller;

import java.util.List;

import cbedoy.giphycontroller.model.GiphyResponse;
import cbedoy.giphycontroller.model.ImagesMetadata;
import cbedoy.giphycontroller.model.Rating;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * GiphyController
 * <p>
 * Created by bedoy on 10/1/17.
 */

public interface IGiphy
{
    interface IGiphyInteractor{

        void requestTreading();
    }

    interface IGiphyPresenter{

        void loadTreading();

        void loadedTreading(List<CBGiphy> data);
    }

    interface IGiphyViewController{

        void onLoadingTreading(List<CBGiphy> data);
    }

    interface IGiphyService{
        @GET("/v1/gifs/search")
        Call<GiphyResponse> searchGifs(
                @Query("q") String searchTerm,
                @Query("limit") Integer limit,
                @Query("offset") Integer offset,
                @Query("rating") Rating rating,
                @Query("fmt") String format);

        @GET("/v1/gifs/search")
        Call<GiphyResponse> searchGifs(
                @Query("q") String searchTerm,
                @Query("limit") Integer limit,
                @Query("api_key") String key);

        @GET("/v1/gifs/translate")
        Call<GiphyResponse> translateText(
                @Query("s") String searchTerm,
                @Query("rating") Rating rating,
                @Query("fmt") String format);


        @GET("/v1/stickers/search")
        Call<GiphyResponse> searchStickers(
                @Query("q") String searchTerm,
                @Query("limit") Integer limit,
                @Query("offset") Integer offset,
                @Query("rating") Rating rating,
                @Query("fmt") String format);

        @GET("/v1/stickers/search")
        Call<GiphyResponse> searchStickers(
                @Query("q") String searchTerm,
                @Query("limit") Integer limit);

        @GET("/v1/gifs/trending")
        Call<GiphyResponse> treading(
                @Query("limit") Integer limit,
                @Query("rating") Rating rating,
                @Query("fmt") String format);

        @GET("/v1/gifs/trending")
        Observable<GiphyResponse> treading(
                @Query("limit") int limit,
                @Query("api_key") String key);

        @GET("/v1/gifs/stickers/trending")
        Call<GiphyResponse> stickersTrending(
                @Query("limit") Integer limit,
                @Query("api_key") String key);
    }
}

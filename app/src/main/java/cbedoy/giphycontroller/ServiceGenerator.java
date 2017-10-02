package cbedoy.giphycontroller;



import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.*;

/**
 * GiphyController
 * <p>
 * Created by bedoy on 10/1/17.
 */

public class ServiceGenerator
{
    private static Retrofit generateRetrofitInstance()
    {
        Level level = BuildConfig.DEBUG ? Level.BODY : Level.NONE;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(level);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        return new Retrofit.Builder()
                .baseUrl("http://api.giphy.com/")
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static <S> S createService(Class<S> serviceClass)
    {
        return generateRetrofitInstance().create(serviceClass);
    }
}

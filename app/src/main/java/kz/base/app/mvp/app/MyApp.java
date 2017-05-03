package kz.base.app.mvp.app;

import android.app.Application;
import android.content.ContextWrapper;
import android.util.Log;

import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;

import kz.base.app.BuildConfig;
import kz.base.app.mvp.common.AuthUtils;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MyApp extends Application {

    private static final String TAG = "MyApp";

    public static final String HOST = "https://naobmen.kz";

    private static MyApp sInstance;

    private RestInterface mApi;

    public MyApp() {
        sInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the Prefs class
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                // add api_token parameter in all HTTP requests
                HttpUrl url = originalHttpUrl.newBuilder().build();

                Request request = original.newBuilder()
                        .addHeader("Accept-Language", "ru")
                        .addHeader("Authorization", "Token token=" + AuthUtils.getToken())
                        .method(original.method(), original.body())
                        .url(url)
                        .build();

                Response response = chain.proceed(request);

                if (BuildConfig.DEBUG) {
                    Log.d(TAG, bodyToString(request.body()));

                    long t1 = System.nanoTime();
                    Log.d(TAG, String.format("Sending request %s on %s%n%s",
                            request.url(), chain.connection(), request.headers()));

                    long t2 = System.nanoTime();
                    Log.d(TAG, String.format("Received response for %s in %.1fms%n%s",
                            response.request().url(), (t2 - t1) / 1e6d, response.headers()));
                }
                return response;
            }
        });
        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST + "/api/v1/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();
        mApi = retrofit.create(RestInterface.class);
    }

    private String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    public static MyApp get() {
        return sInstance;
    }

    public RestInterface getApi() {
        return mApi;
    }
}
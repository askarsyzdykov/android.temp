package kz.base.app.mvp.app;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestInterface {

    @GET("dummy")
    Call<Void> dummy();

}

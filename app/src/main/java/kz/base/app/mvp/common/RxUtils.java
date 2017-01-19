package kz.base.app.mvp.common;

import java.io.IOException;

import kz.base.app.mvp.app.MyError;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Date: 18.01.2016
 * Time: 14:37
 *
 * @author Yuri Shmakov
 */
public class RxUtils {
    public static <T> Observable<T> wrapRetrofitCall(final Call<T> call) {
        return Observable.create(subscriber ->
        {
            final Response<T> execute;
            try {
                execute = call.execute();
            } catch (IOException e) {
                subscriber.onError(e);
                return;
            }

            if (execute.isSuccessful()) {
                subscriber.onNext(execute.body());
            } else {
                subscriber.onError(new MyError(execute.errorBody()));
            }
        });
    }

    public static <T> Observable<T> wrapAsync(Observable<T> observable) {
        return wrapAsync(observable, Schedulers.io());
    }

    public static <T> Observable<T> wrapAsync(Observable<T> observable, Scheduler scheduler) {
        return observable
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread());
    }
}

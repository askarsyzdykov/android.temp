package kz.base.app.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import kz.base.app.mvp.app.MyApp;
import kz.base.app.mvp.common.RxUtils;
import kz.base.app.mvp.views.SplashView;
import rx.Observable;

/**
 * Created by askar on 19.07.16.
 */

@InjectViewState
public class SplashPresenter extends MvpPresenter<SplashView> {
    private boolean mIsInLoading;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        loadData(false);
    }

    public void loadData(boolean isRefreshing) {
        loadData(false, isRefreshing);
    }

    private void loadData(boolean isPageLoading, boolean isRefreshing) {
        if (mIsInLoading) {
            return;
        }
        mIsInLoading = true;

        getViewState().hideError();
        getViewState().onStartLoading();

        final Observable<Void> observable = RxUtils.wrapRetrofitCall(MyApp.get().getApi().dummy());

        RxUtils.wrapAsync(observable)
                .subscribe(response -> {
                    getViewState().onFinishLoading();
                    getViewState().onSuccess();
                }, error -> {
                    getViewState().onFinishLoading();
                    getViewState().showError(error.toString());
                });
    }
}
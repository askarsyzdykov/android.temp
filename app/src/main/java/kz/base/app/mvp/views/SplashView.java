package kz.base.app.mvp.views;

import com.arellomobile.mvp.MvpView;

public interface SplashView extends MvpView {
    void showError(String message);

    void hideError();

    void onStartLoading();

    void onFinishLoading();

    void onSuccess();
}

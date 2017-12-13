package kz.base.app.ui.activities;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.Toast;

import com.akexorcist.localizationactivity.core.LocalizationActivityDelegate;
import com.akexorcist.localizationactivity.core.OnLocaleChangedListener;
import com.arellomobile.mvp.MvpAppCompatActivity;

import java.util.Locale;

import butterknife.ButterKnife;
import kz.base.app.R;

public abstract class BaseActivity extends MvpAppCompatActivity implements OnLocaleChangedListener {

    private Toast mToast;
    protected ViewDataBinding mViewDataBinding;

    private LocalizationActivityDelegate localizationDelegate = new LocalizationActivityDelegate(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        localizationDelegate.addOnLocaleChangedListener(this);
        localizationDelegate.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            setSupportActionBar(findViewById(R.id.toolbar));
            actionBar = getSupportActionBar();
        }
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        localizationDelegate.onResume(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(localizationDelegate.attachBaseContext(newBase));
    }

    @Override
    public Context getApplicationContext() {
        return localizationDelegate.getApplicationContext(super.getApplicationContext());
    }

    @Override
    public Resources getResources() {
        return localizationDelegate.getResources(super.getResources());
    }

    public final void setLanguage(String language) {
        localizationDelegate.setLanguage(this, language);
    }

    public final void setLanguage(Locale locale) {
        localizationDelegate.setLanguage(this, locale);
    }

    public final void setDefaultLanguage(String language) {
        localizationDelegate.setDefaultLanguage(language);
    }

    public final void setDefaultLanguage(Locale locale) {
        localizationDelegate.setDefaultLanguage(locale);
    }

    public final Locale getCurrentLanguage() {
        return localizationDelegate.getLanguage(this);
    }

    // Just override method locale change event
    @Override
    public void onBeforeLocaleChanged() {
    }

    @Override
    public void onAfterLocaleChanged() {
    }

    protected abstract int getLayoutId();

    protected abstract boolean displayHomeAsUpEnabled();

    protected void showToast(String text) {
        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        mToast.show();
    }

    protected void showToast(int resId) {
        showToast(getString(resId));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}

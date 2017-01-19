package kz.base.app.ui.activities;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.akexorcist.localizationactivity.LocalizationDelegate;
import com.akexorcist.localizationactivity.OnLocaleChangedListener;
import com.arellomobile.mvp.MvpAppCompatActivity;

import java.util.Locale;

import butterknife.ButterKnife;
import kz.base.app.R;

public abstract class BaseActivity extends MvpAppCompatActivity implements OnLocaleChangedListener {

    private Toast mToast;
    protected ViewDataBinding mViewDataBinding;

    private LocalizationDelegate localizationDelegate = new LocalizationDelegate(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        localizationDelegate.addOnLocaleChengedListener(this);
        localizationDelegate.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
            actionBar = getSupportActionBar();
        }
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        localizationDelegate.onResume();
    }

    public final void setLanguage(String language) {
        localizationDelegate.setLanguage(language);
    }

    public final void setLanguage(Locale locale) {
        localizationDelegate.setLanguage(locale);
    }

    public final void setDefaultLanguage(String language) {
        localizationDelegate.setDefaultLanguage(language);
    }

    public final void setDefaultLanguage(Locale locale) {
        localizationDelegate.setDefaultLanguage(locale);
    }

    public final String getLanguage() {
        return localizationDelegate.getLanguage();
    }

    public final Locale getLocale() {
        return localizationDelegate.getLocale();
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

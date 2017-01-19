package kz.base.app.ui.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

abstract class BaseFragment extends MvpAppCompatFragment {

    protected Unbinder unbinder;

    private Toast mToast;
    ViewDataBinding mViewDataBinding;

    protected abstract int getLayoutId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        View view = mViewDataBinding != null ? mViewDataBinding.getRoot() : inflater.inflate(getLayoutId(), container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected void showToast(String text) {
        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        mToast.show();
    }

    protected void showToast(int resId) {
        showToast(getString(resId));
    }
}

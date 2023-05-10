package com.sortscript.digitalkasaan.ui.base;

import android.app.ProgressDialog;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sortscript.digitalkasaan.R;
import com.sortscript.digitalkasaan.other.utils.CommonUtils;

public abstract class BaseFragment extends Fragment {

    ProgressDialog mProgressDialog;

    protected void showLoading() {
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog = CommonUtils.showLoadingDialog(getContext(), false);
        } else if (mProgressDialog == null) {
            mProgressDialog = CommonUtils.showLoadingDialog(getContext(), false);
        }
    }

    public void hideLoading() {
        mProgressDialog.cancel();
    }

    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void replaceFragmentMethod(Fragment frag, String tag, Boolean addToStack, Boolean clearStack,
                                      FragmentActivity fa) {
        if (clearStack)
            fa.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = fa.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, frag, tag);
        if (addToStack)
            ft.addToBackStack(tag);
        ft.commit();

    }

}

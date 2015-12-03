package com.guanqing.subredditor.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Window;

import com.guanqing.subredditor.R;

/**
 * Created by Guanqing on 2015/12/3.
 * Pop out and show a boarderless image/gif view
 */
public class ZoomViewDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        return dialog;
    }


}

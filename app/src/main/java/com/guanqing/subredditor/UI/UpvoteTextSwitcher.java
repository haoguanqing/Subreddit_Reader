package com.guanqing.subredditor.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextSwitcher;

import com.guanqing.subredditor.Util.ToastUtil;

/**
 * Created by Guanqing on 2015/12/1.
 */
public class UpvoteTextSwitcher extends TextSwitcher {
    private boolean isUpvoted = false;
    private Context context;

    public UpvoteTextSwitcher(Context context){
        super(context);
        this.context = context;

    }

    public UpvoteTextSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public UpvoteTextSwitcher getInstance(){
        return this;
    }

    /**
     * set upvote status
     */
    public void setListener(int upvoteCount){
        final int c = upvoteCount;
        getInstance().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getInstance().setText(isUpvoted ? c + " upvotes" : c + 1 + " upvotes");
                ToastUtil.show(context, isUpvoted ? "Upvote cancelled" : "Upvoted");
                isUpvoted = !isUpvoted;
            }
        });
    }
}
package com.guanqing.subredditor.ui.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.guanqing.subredditor.R;
import com.guanqing.subredditor.util.ToastUtil;

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
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getInstance().setText(isUpvoted ? c + "" : c + 1 + "");
                ToastUtil.show(isUpvoted ? "Upvote cancelled" : "Upvoted");
                isUpvoted = !isUpvoted;
            }
        });
    }


    //provide access from outside the switcher to set text as well as text color after votes
    public void upVote(int count){
        setText(isUpvoted ? count + "" : count + 1 + "");
        TextView textView = (TextView) getChildAt(1);
        textView.setTextColor(getResources().getColor(R.color.text_upvote_counter));
    }

    public void downVote(int count) {
        setText(isUpvoted ? count + "" : count -1 + "");
        TextView textView = (TextView) getChildAt(1);
        textView.setTextColor(getResources().getColor(R.color.text_downvote_counter));
    }
}

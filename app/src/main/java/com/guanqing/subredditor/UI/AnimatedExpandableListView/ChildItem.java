package com.guanqing.subredditor.UI.AnimatedExpandableListView;

import android.widget.TextView;

/**
 * Created by Guanqing on 2015/12/14.
 */
public class ChildItem {

    String title;
    //boolean favorite;

    public ChildItem(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static class ChildHolder {
        TextView title;
        //ImageView favorite;
    }
}

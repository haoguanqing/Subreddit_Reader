package com.guanqing.subredditor.UI.ui.AnimatedExpandableListView;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guanqing on 2015/12/14.
 */
public class GroupItem {

    String title;
    List<ChildItem> items = new ArrayList<ChildItem>();

    public GroupItem(String title){
        this.title = title;
    }

    public void addChild(ChildItem childItem){
        items.add(childItem);
    }

    public static class GroupHolder {
        TextView title;
    }

}
